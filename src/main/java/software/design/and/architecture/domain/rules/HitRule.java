package software.design.and.architecture.domain.rules;

public interface HitRule {
    boolean forfeitOnHit(boolean hitDetected);
}
