package software.design.and.architecture.usecase.model;

import software.design.and.architecture.domain.model.DiceMode;
import software.design.and.architecture.domain.model.GameConfig;

public record GameConfigSnapshot(int boardPositions, int tailPositions, int playerCount, DiceMode diceMode, boolean exactEndRequired, boolean forfeitOnHit) {

    public static GameConfigSnapshot from(GameConfig config) {
        return new GameConfigSnapshot(config.boardPositions(), config.tailPositions(), config.playerCount(), config.diceMode(), config.exactEndRequired(), config.forfeitOnHit());
    }

    public GameConfig toGameConfig() {
        return GameConfig.of(playerCount, diceMode, exactEndRequired, forfeitOnHit);
    }
}
