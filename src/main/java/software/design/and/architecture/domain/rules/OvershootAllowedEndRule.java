package software.design.and.architecture.domain.rules;

public final class OvershootAllowedEndRule implements EndRule {

    @Override
    public MoveDecision decide(int beforeIndex, int roll, int endIndex) {
        int tentative = beforeIndex + roll;
        int afterIndex = Math.min(tentative, endIndex);
        boolean reachedEnd = afterIndex == endIndex;
        boolean overshoot = tentative > endIndex;
        return new MoveDecision(afterIndex, true, reachedEnd, overshoot);
    }
}
