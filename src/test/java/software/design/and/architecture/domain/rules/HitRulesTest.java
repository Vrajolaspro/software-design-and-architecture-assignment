package software.design.and.architecture.domain.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HitRulesTest {

    @Test
    void allowHitRuleShouldNeverForfeit() {
        HitRule rule = new AllowHitRule();
        assertFalse(rule.forfeitOnHit(true));
        assertFalse(rule.forfeitOnHit(false));
    }

    @Test
    void forfeitOnHitRuleShouldForfeitOnlyWhenHitDetected() {
        HitRule rule = new ForfeitOnHitRule();
        assertTrue(rule.forfeitOnHit(true));
        assertFalse(rule.forfeitOnHit(false));
    }
}
