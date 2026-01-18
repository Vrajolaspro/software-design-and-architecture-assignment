package software.design.and.architecture.usecase.service;

import org.springframework.stereotype.Service;
import software.design.and.architecture.domain.model.*;
import software.design.and.architecture.domain.rules.MoveDecision;
import software.design.and.architecture.usecase.model.GameOutcome;
import software.design.and.architecture.usecase.port.DiceRollSource;
import software.design.and.architecture.usecase.port.GamePresenter;

import java.util.*;

@Service
public class PlayGameUseCase {

    public void playToCompletion(GameConfig config, DiceRollSource dice, GamePresenter presenter) {
        playToCompletionWithOutcome(config, dice, presenter);
    }

    public GameOutcome playToCompletionWithOutcome(GameConfig config, DiceRollSource dice, GamePresenter presenter) {
        List<PlayerColor> players = playersFor(config);
        Map<PlayerColor, Route> routes = buildRoutes(config, players);
        Map<PlayerColor, Integer> indices = new EnumMap<>(PlayerColor.class);
        Map<PlayerColor, Integer> turns = new EnumMap<>(PlayerColor.class);
        for (PlayerColor p : players) {
            indices.put(p, 0);
            turns.put(p, 0);
        }
        int currentIdx = 0;
        while (true) {
            PlayerColor current = players.get(currentIdx);
            OptionalInt maybeRoll = dice.nextRoll(config, current);
            if (maybeRoll.isEmpty()) {
                presenter.showMessage("Game ended (no more rolls).");
                return GameOutcome.notCompleted();
            }
            int roll = maybeRoll.getAsInt();
            Route route = routes.get(current);
            int beforeIndex = indices.get(current);
            Position from = route.positionAt(beforeIndex);
            int endIndex = route.endIndex();
            MoveDecision decision = config.endRule().decide(beforeIndex, roll, endIndex);
            int afterIndex = decision.afterIndex();
            Position attemptedTo = route.positionAt(afterIndex);
            boolean hit = isHit(current, attemptedTo, routes, indices, players);
            boolean forfeitedOnHit = config.hitRule().forfeitOnHit(hit);
            int finalAfterIndex = afterIndex;
            Position finalTo = attemptedTo;
            boolean finalMoved = decision.moved();
            if (forfeitedOnHit) {
                finalAfterIndex = beforeIndex;
                finalTo = from;
                finalMoved = false;
            }
            indices.put(current, finalAfterIndex);
            boolean reachedEnd = !forfeitedOnHit && decision.reachedEnd();
            int newTurnCount = turns.get(current) + 1;
            turns.put(current, newTurnCount);
            presenter.showTurn(
                    new MoveResult(
                            current,
                            roll,
                            from,
                            attemptedTo,
                            finalTo,
                            hit,
                            reachedEnd,
                            finalMoved,
                            decision.overshoot(),
                            forfeitedOnHit
                    ),
                    newTurnCount
            );
            if (reachedEnd) {
                int totalTurns = turns.values().stream().mapToInt(Integer::intValue).sum();
                presenter.showWinner(current, newTurnCount, totalTurns);
                return new GameOutcome(current, newTurnCount, totalTurns, true);
            }
            currentIdx = (currentIdx + 1) % players.size();
        }
    }

    private List<PlayerColor> playersFor(GameConfig config) {
        if (config.playerCount() == 4) {
            return List.of(PlayerColor.RED, PlayerColor.GREEN, PlayerColor.BLUE, PlayerColor.YELLOW);
        }
        return List.of(PlayerColor.RED, PlayerColor.BLUE);
    }

    private boolean isHit(PlayerColor mover, Position destination, Map<PlayerColor, Route> routes, Map<PlayerColor, Integer> indices, List<PlayerColor> players) {
        for (PlayerColor other : players) {
            if (other == mover) continue;
            Position otherPos = routes.get(other).positionAt(indices.get(other));
            if (destination.equals(otherPos)) {
                return true;
            }
        }
        return false;
    }

    private Map<PlayerColor, Route> buildRoutes(GameConfig config, List<PlayerColor> players) {
        Map<PlayerColor, Route> routes = new EnumMap<>(PlayerColor.class);
        for (PlayerColor p : players) {
            int home = homeStartFor(p, config.playerCount());
            List<Position> path = buildPath(home, config.boardPositions(), config.tailPositions(), p.tailPrefix());
            routes.put(p, new Route(path));
        }

        return routes;
    }

    private int homeStartFor(PlayerColor player, int playerCount) {
        if (playerCount == 2) {
            return (player == PlayerColor.RED) ? 1 : 10;
        }
        return switch (player) {
            case RED -> 1;
            case GREEN -> 6;
            case BLUE -> 10;
            case YELLOW -> 15;
        };
    }

    private List<Position> buildPath(int start, int board, int tail, String tailPrefix) {
        List<Position> path = new ArrayList<>(board + tail);
        for (int i = start; i <= board; i++) {
            path.add(Position.of(String.valueOf(i)));
        }
        for (int i = 1; i < start; i++) {
            path.add(Position.of(String.valueOf(i)));
        }
        for (int i = 1; i <= tail; i++) {
            path.add(Position.of(tailPrefix + i));
        }
        return path;
    }
}
