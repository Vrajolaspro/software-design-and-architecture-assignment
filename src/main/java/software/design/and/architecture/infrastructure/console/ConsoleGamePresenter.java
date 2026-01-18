package software.design.and.architecture.infrastructure.console;

import org.springframework.stereotype.Component;
import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.domain.model.MoveResult;
import software.design.and.architecture.domain.model.PlayerColor;
import software.design.and.architecture.domain.model.Position;
import software.design.and.architecture.usecase.port.GamePresenter;

@Component
public class ConsoleGamePresenter implements GamePresenter {

    @Override
    public void showBanner(GameConfig config) {
        System.out.println("=== Simple Frustration ===");
        System.out.println(config.description());
        System.out.printf("Players=%d Board positions=%d Tail positions=%d%n", config.playerCount(), config.boardPositions(), config.tailPositions());
        System.out.println();
    }

    @Override
    public void showTurn(MoveResult move, int playerTurnCount) {
        String player = move.player().displayName();
        System.out.printf("%s turn %d rolls %d%n", player, playerTurnCount, move.roll());
        if (move.overshoot() && !move.moved()) {
            System.out.printf("%s overshoots!%n", player);
            System.out.printf("%s remains at %s%n", player, formatPosition(move.from(), move.player()));
            return;
        }
        if (move.hit()) {
            System.out.printf("HIT at position %s!%n", move.attemptedTo().label());
        }
        if (move.forfeitedOnHit()) {
            System.out.printf("%s remains at %s%n", player, formatPosition(move.from(), move.player()));
            return;
        }
        System.out.printf("%s moves from %s to %s%n", player, formatPosition(move.from(), move.player()), formatPosition(move.to(), move.player()));
        if (move.reachedEnd()) {
            System.out.println();
        }
    }

    @Override
    public void showWinner(PlayerColor winner, int winnerTurns, int totalTurns) {
        System.out.printf("%s wins in %d turns!%n", winner.displayName(), winnerTurns);
        System.out.printf("Total turns %d%n", totalTurns);
    }

    @Override
    public void showMessage(String message) {
        System.out.println(message);
    }

    private String formatPosition(Position position, PlayerColor player) {
        String label = position.label();
        if (player == PlayerColor.RED && label.equals("1")) return "Home (Position 1)";
        if (player == PlayerColor.GREEN && label.equals("6")) return "Home (Position 6)";
        if (player == PlayerColor.BLUE && label.equals("10")) return "Home (Position 10)";
        if (player == PlayerColor.YELLOW && label.equals("15")) return "Home (Position 15)";
        if (label.length() >= 2) {
            char prefix = label.charAt(0);
            String num = label.substring(1);
            boolean isTailPrefix = prefix == 'R' || prefix == 'B' || prefix == 'G' || prefix == 'Y';
            if (isTailPrefix) {
                if (num.equals("3")) {
                    return "End (Position " + label + ")";
                }
                return "Tail Position " + label;
            }
        }
        return "Position " + label;
    }
}
