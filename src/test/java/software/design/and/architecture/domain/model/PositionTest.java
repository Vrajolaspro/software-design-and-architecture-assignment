package software.design.and.architecture.domain.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PositionTest {

    @Test
    void positionsWithSameLabel_areEqual_andHaveSameHashCode() {
        Position a = Position.of("12");
        Position b = Position.of("12");

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void positionsWithDifferentLabels_areNotEqual() {
        Position a = Position.of("12");
        Position b = Position.of("13");

        assertNotEquals(a, b);
    }

    @Test
    void toString_returnsLabel() {
        assertEquals("R2", Position.of("R2").toString());
        assertEquals("10", Position.of("10").toString());
    }
}
