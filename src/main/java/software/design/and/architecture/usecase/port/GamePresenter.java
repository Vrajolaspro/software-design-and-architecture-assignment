package software.design.and.architecture.usecase.port;

import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.domain.model.MoveResult;
import software.design.and.architecture.domain.model.PlayerColor;

public interface GamePresenter {
    void showBanner(GameConfig config);

    void showTurn(MoveResult move, int playerTurnCount);

    void showWinner(PlayerColor winner, int winnerTurns, int totalTurns);

    void showMessage(String message);
}
