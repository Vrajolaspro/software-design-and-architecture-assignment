package software.design.and.architecture.domain.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExactEndRuleTest {

    @Test
    void decide_whenOvershoot_shouldNotMove_andShouldFlagOvershoot() {
        ExactEndRule rule = new ExactEndRule();
        int beforeIndex = 19;
        int roll = 5;
        int endIndex = 20;
        MoveDecision decision = rule.decide(beforeIndex, roll, endIndex);
        assertEquals(beforeIndex, decision.afterIndex());
        assertFalse(decision.moved());
        assertFalse(decision.reachedEnd());
        assertTrue(decision.overshoot());
    }

    @Test
    void decide_whenExactEnd_shouldMove_andReachEnd() {
        ExactEndRule rule = new ExactEndRule();
        int beforeIndex = 18;
        int roll = 2;
        int endIndex = 20;
        MoveDecision decision = rule.decide(beforeIndex, roll, endIndex);
        assertEquals(20, decision.afterIndex());
        assertTrue(decision.moved());
        assertTrue(decision.reachedEnd());
        assertFalse(decision.overshoot());
    }

    @Test
    void decide_whenNotAtEnd_shouldMove_normally() {
        ExactEndRule rule = new ExactEndRule();
        int beforeIndex = 5;
        int roll = 3;
        int endIndex = 20;
        MoveDecision decision = rule.decide(beforeIndex, roll, endIndex);
        assertEquals(8, decision.afterIndex());
        assertTrue(decision.moved());
        assertFalse(decision.reachedEnd());
        assertFalse(decision.overshoot());
    }
}
