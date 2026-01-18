package software.design.and.architecture.domain.rules;

public final class AllowHitRule implements HitRule {
    @Override
    public boolean forfeitOnHit(boolean hitDetected) {
        return false;
    }
}
