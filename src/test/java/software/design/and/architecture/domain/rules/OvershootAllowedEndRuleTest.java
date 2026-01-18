package software.design.and.architecture.domain.rules;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OvershootAllowedEndRuleTest {

    @Test
    void decide_whenOvershoot_shouldMoveToEnd_andReachEnd() {
        OvershootAllowedEndRule rule = new OvershootAllowedEndRule();
        int beforeIndex = 19;
        int roll = 5;
        int endIndex = 20;
        MoveDecision decision = rule.decide(beforeIndex, roll, endIndex);
        assertEquals(endIndex, decision.afterIndex());
        assertTrue(decision.moved());
        assertTrue(decision.reachedEnd());
        assertTrue(decision.overshoot());
    }

    @Test
    void decide_whenExactEnd_shouldReachEnd_withoutOvershoot() {
        OvershootAllowedEndRule rule = new OvershootAllowedEndRule();
        int beforeIndex = 18;
        int roll = 2;
        int endIndex = 20;
        MoveDecision decision = rule.decide(beforeIndex, roll, endIndex);
        assertEquals(endIndex, decision.afterIndex());
        assertTrue(decision.moved());
        assertTrue(decision.reachedEnd());
        assertFalse(decision.overshoot());
    }

    @Test
    void decide_whenNotAtEnd_shouldMove_normally() {
        OvershootAllowedEndRule rule = new OvershootAllowedEndRule();
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
