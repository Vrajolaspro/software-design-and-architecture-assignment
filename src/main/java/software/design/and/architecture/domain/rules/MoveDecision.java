package software.design.and.architecture.domain.rules;

public final class MoveDecision {
    private final int afterIndex;
    private final boolean moved;
    private final boolean reachedEnd;
    private final boolean overshoot;

    public MoveDecision(int afterIndex, boolean moved, boolean reachedEnd, boolean overshoot) {
        this.afterIndex = afterIndex;
        this.moved = moved;
        this.reachedEnd = reachedEnd;
        this.overshoot = overshoot;
    }

    public int afterIndex() {
        return afterIndex;
    }

    public boolean moved() {
        return moved;
    }

    public boolean reachedEnd() {
        return reachedEnd;
    }

    public boolean overshoot() {
        return overshoot;
    }
}
