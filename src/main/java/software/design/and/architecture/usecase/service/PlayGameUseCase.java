package software.design.and.architecture.usecase.service;

import org.springframework.stereotype.Service;
import software.design.and.architecture.domain.model.*;
import software.design.and.architecture.usecase.port.DiceRollSource;
import software.design.and.architecture.usecase.port.GamePresenter;

import java.util.*;

@Service
public class PlayGameUseCase {

    public void playToCompletion(GameConfig config, DiceRollSource dice, GamePresenter presenter) {
        Map<PlayerColor, Route> routes = buildRoutes(config);
        Map<PlayerColor, Integer> indices = new EnumMap<>(PlayerColor.class);
        Map<PlayerColor, Integer> turns = new EnumMap<>(PlayerColor.class);
        indices.put(PlayerColor.RED, 0);
        indices.put(PlayerColor.BLUE, 0);
        turns.put(PlayerColor.RED, 0);
        turns.put(PlayerColor.BLUE, 0);
        PlayerColor current = PlayerColor.RED;
        while (true) {
            OptionalInt maybeRoll = dice.nextRoll(current);
            if (maybeRoll.isEmpty()) {
                presenter.showMessage("Game ended (no more rolls).");
                return;
            }
            int roll = maybeRoll.getAsInt();
            Route route = routes.get(current);
            int beforeIndex = indices.get(current);
            Position from = route.positionAt(beforeIndex);
            int endIndex = route.endIndex();
            int afterIndex = beforeIndex + roll;
            if (afterIndex > endIndex) {
                afterIndex = endIndex;
            }
            Position to = route.positionAt(afterIndex);
            boolean hit = isHit(current, to, routes, indices);
            indices.put(current, afterIndex);
            int newTurnCount = turns.get(current) + 1;
            turns.put(current, newTurnCount);
            boolean reachedEnd = afterIndex == endIndex;
            presenter.showTurn(new MoveResult(current, roll, from, to, hit, reachedEnd), newTurnCount);
            if (reachedEnd) {
                int totalTurns = turns.get(PlayerColor.RED) + turns.get(PlayerColor.BLUE);
                presenter.showWinner(current, newTurnCount, totalTurns);
                return;
            }
            current = (current == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
        }
    }

    private boolean isHit(PlayerColor mover, Position destination, Map<PlayerColor, Route> routes, Map<PlayerColor, Integer> indices) {
        PlayerColor other = (mover == PlayerColor.RED) ? PlayerColor.BLUE : PlayerColor.RED;
        Position otherPos = routes.get(other).positionAt(indices.get(other));
        return destination.equals(otherPos);
    }

    private Map<PlayerColor, Route> buildRoutes(GameConfig config) {
        Map<PlayerColor, Route> routes = new EnumMap<>(PlayerColor.class);
        routes.put(PlayerColor.RED, new Route(buildRedPath(config.boardPositions(), config.tailPositions())));
        routes.put(PlayerColor.BLUE, new Route(buildBluePath(config.boardPositions(), config.tailPositions())));
        return routes;
    }

    private List<Position> buildRedPath(int board, int tail) {
        List<Position> path = new ArrayList<>(board + tail);
        for (int i = 1; i <= board; i++) {
            path.add(Position.of(String.valueOf(i)));
        }
        for (int i = 1; i <= tail; i++) {
            path.add(Position.of("R" + i));
        }
        return path;
    }

    private List<Position> buildBluePath(int board, int tail) {
        List<Position> path = new ArrayList<>(board + tail);
        for (int i = 10; i <= board; i++) {
            path.add(Position.of(String.valueOf(i)));
        }
        for (int i = 1; i <= 9; i++) {
            path.add(Position.of(String.valueOf(i)));
        }
        for (int i = 1; i <= tail; i++) {
            path.add(Position.of("B" + i));
        }
        return path;
    }
}
