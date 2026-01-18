package software.design.and.architecture.domain.model;

public final class GameConfig {
    private final int boardPositions;
    private final int tailPositions;

    private GameConfig(int boardPositions, int tailPositions) {
        this.boardPositions = boardPositions;
        this.tailPositions = tailPositions;
    }

    public static GameConfig basicTwoPlayer() {
        return new GameConfig(18, 3);
    }

    public int boardPositions() {
        return boardPositions;
    }

    public int tailPositions() {
        return tailPositions;
    }
}
