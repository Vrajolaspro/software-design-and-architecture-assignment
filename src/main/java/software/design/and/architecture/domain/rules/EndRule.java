package software.design.and.architecture.domain.rules;

public interface EndRule {
    MoveDecision decide(int beforeIndex, int roll, int endIndex);
}
