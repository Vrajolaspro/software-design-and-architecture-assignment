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
        System.out.println("=== Simple Frustration (Basic Game) ===");
        System.out.printf("Board positions=%d Tail positions=%d%n", config.boardPositions(), config.tailPositions());
        System.out.println("HITS are ignored, multiple players can occupy the same position");
        System.out.println("Player can land on or beyond the END position to win");
        System.out.println();
    }

    @Override
    public void showTurn(MoveResult move, int playerTurnCount) {
        String player = move.player().displayName();
        System.out.printf("%s turn %d rolls %d%n", player, playerTurnCount, move.roll());
        if (move.hit()) {
            System.out.printf("%s hit!%n", move.to().label());
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
        if (player == PlayerColor.RED && position.label().equals("1")) {
            return "Home (Position 1)";
        }
        if (player == PlayerColor.BLUE && position.label().equals("10")) {
            return "Home (Position 10)";
        }
        if (position.label().startsWith("R") || position.label().startsWith("B")) {
            String tailNum = position.label().substring(1);
            if (tailNum.equals("3")) {
                return "End (Position " + position.label() + ")";
            }
            return "Tail Position " + position.label();
        }
        return "Position " + position.label();
    }
}
