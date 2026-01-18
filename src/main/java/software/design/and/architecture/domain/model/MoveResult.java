package software.design.and.architecture.domain.model;

public final class MoveResult {
    private final PlayerColor player;
    private final int roll;
    private final Position from;
    private final Position attemptedTo;
    private final Position to;
    private final boolean hit;
    private final boolean reachedEnd;
    private final boolean moved;
    private final boolean overshoot;
    private final boolean forfeitedOnHit;

    public MoveResult(PlayerColor player, int roll, Position from, Position attemptedTo, Position to, boolean hit, boolean reachedEnd, boolean moved, boolean overshoot, boolean forfeitedOnHit) {
        this.player = player;
        this.roll = roll;
        this.from = from;
        this.attemptedTo = attemptedTo;
        this.to = to;
        this.hit = hit;
        this.reachedEnd = reachedEnd;
        this.moved = moved;
        this.overshoot = overshoot;
        this.forfeitedOnHit = forfeitedOnHit;
    }

    public PlayerColor player() { return player; }
    public int roll() { return roll; }
    public Position from() { return from; }
    public Position attemptedTo() { return attemptedTo; }
    public Position to() { return to; }
    public boolean hit() { return hit; }
    public boolean reachedEnd() { return reachedEnd; }
    public boolean moved() { return moved; }
    public boolean overshoot() { return overshoot; }
    public boolean forfeitedOnHit() { return forfeitedOnHit; }
}
