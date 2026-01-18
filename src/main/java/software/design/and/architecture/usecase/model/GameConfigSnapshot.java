package software.design.and.architecture.usecase.model;

import software.design.and.architecture.domain.model.DiceMode;
import software.design.and.architecture.domain.model.GameConfig;

public record GameConfigSnapshot(int boardPositions, int tailPositions, DiceMode diceMode, boolean exactEndRequired,
                                 boolean forfeitOnHit) {
    public static GameConfigSnapshot from(GameConfig config) {
        boolean exactEnd = config.description().toLowerCase().contains("exact end");
        boolean forfeitHit = config.description().toLowerCase().contains("forfeit on hit");
        return new GameConfigSnapshot(config.boardPositions(), config.tailPositions(), config.diceMode(), exactEnd, forfeitHit);
    }

    public GameConfig toGameConfig() {
        return GameConfig.of(diceMode, exactEndRequired, forfeitOnHit);
    }
}
