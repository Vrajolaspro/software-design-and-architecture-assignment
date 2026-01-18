package software.design.and.architecture.domain.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HitRulesTest {

    @Test
    void allowHitRule_shouldNeverForfeit() {
        HitRule rule = new AllowHitRule();
        assertFalse(rule.forfeitOnHit(true));
        assertFalse(rule.forfeitOnHit(false));
    }

    @Test
    void forfeitOnHitRule_shouldForfeitOnlyWhenHitDetected() {
        HitRule rule = new ForfeitOnHitRule();
        assertTrue(rule.forfeitOnHit(true));
        assertFalse(rule.forfeitOnHit(false));
    }
}
