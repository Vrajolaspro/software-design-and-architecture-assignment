package software.design.and.architecture.domain.rules;

public final class ForfeitOnHitRule implements HitRule {
    @Override
    public boolean forfeitOnHit(boolean hitDetected) {
        return hitDetected;
    }
}