package software.design.and.architecture.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PositionTest {

    @Test
    void positionsWithSameLabelAreEqualAndHaveSameHashCode() {
        Position a = Position.of("12");
        Position b = Position.of("12");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void positionsWithDifferentLabelsAreNotEqual() {
        Position a = Position.of("12");
        Position b = Position.of("13");
        assertNotEquals(a, b);
    }

    @Test
    void toStringReturnsLabel() {
        assertEquals("R2", Position.of("R2").toString());
        assertEquals("10", Position.of("10").toString());
    }
}
