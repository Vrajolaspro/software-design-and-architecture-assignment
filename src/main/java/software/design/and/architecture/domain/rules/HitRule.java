package software.design.and.architecture.domain.rules;

public interface HitRule {
    /**
     * @return true if the move should be forfeited (player stays in place)
     */
    boolean forfeitOnHit(boolean hitDetected);
}
