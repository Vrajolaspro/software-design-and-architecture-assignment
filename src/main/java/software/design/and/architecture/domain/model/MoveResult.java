package software.design.and.architecture.domain.model;

public final class MoveResult {
    private final PlayerColor player;
    private final int roll;
    private final Position from;
    private final Position to;
    private final boolean hit;
    private final boolean reachedEnd;

    public MoveResult(PlayerColor player, int roll, Position from, Position to, boolean hit, boolean reachedEnd) {
        this.player = player;
        this.roll = roll;
        this.from = from;
        this.to = to;
        this.hit = hit;
        this.reachedEnd = reachedEnd;
    }

    public PlayerColor player() {
        return player;
    }

    public int roll() {
        return roll;
    }

    public Position from() {
        return from;
    }

    public Position to() {
        return to;
    }

    public boolean hit() {
        return hit;
    }

    public boolean reachedEnd() {
        return reachedEnd;
    }
}
