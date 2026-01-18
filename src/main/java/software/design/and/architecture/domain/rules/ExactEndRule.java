package software.design.and.architecture.domain.rules;

public final class ExactEndRule implements EndRule {

    @Override
    public MoveDecision decide(int beforeIndex, int roll, int endIndex) {
        int tentative = beforeIndex + roll;
        if (tentative > endIndex) {
            return new MoveDecision(beforeIndex, false, false, true);
        }
        boolean reachedEnd = tentative == endIndex;
        return new MoveDecision(tentative, true, reachedEnd, false);
    }
}
