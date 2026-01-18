package software.design.and.architecture.domain.model;

import software.design.and.architecture.domain.rules.EndRule;
import software.design.and.architecture.domain.rules.ExactEndRule;
import software.design.and.architecture.domain.rules.OvershootAllowedEndRule;

public final class GameConfig {
    private final int boardPositions;
    private final int tailPositions;
    private final EndRule endRule;

    private GameConfig(int boardPositions, int tailPositions, EndRule endRule) {
        this.boardPositions = boardPositions;
        this.tailPositions = tailPositions;
        this.endRule = endRule;
    }

    public static GameConfig basicTwoPlayer() {
        return new GameConfig(18, 3, new OvershootAllowedEndRule());
    }

    public static GameConfig exactEndTwoPlayer() {
        return new GameConfig(18, 3, new ExactEndRule());
    }

    public int boardPositions() {
        return boardPositions;
    }

    public int tailPositions() {
        return tailPositions;
    }

    public EndRule endRule() {
        return endRule;
    }
}
