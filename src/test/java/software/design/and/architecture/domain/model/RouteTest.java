package software.design.and.architecture.domain.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RouteTest {

    @Test
    void constructorRejectsNullPath() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Route(null));
        assertEquals("Route path must not be empty", ex.getMessage());
    }

    @Test
    void constructorRejectsEmptyPath() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> new Route(List.of()));
        assertEquals("Route path must not be empty", ex.getMessage());
    }

    @Test
    void positionAtIndexLessThanOrEqualZero_returnsFirst() {
        Route route = new Route(List.of(
                Position.of("1"),
                Position.of("2"),
                Position.of("3")
        ));
        assertEquals(Position.of("1"), route.positionAt(0));
        assertEquals(Position.of("1"), route.positionAt(-5));
    }

    @Test
    void positionAtIndexGreaterThanOrEqualEnd_returnsLast() {
        Route route = new Route(List.of(
                Position.of("1"),
                Position.of("2"),
                Position.of("3")
        ));
        assertEquals(Position.of("3"), route.positionAt(2));   // end
        assertEquals(Position.of("3"), route.positionAt(999)); // beyond end
    }

    @Test
    void positionAtNormalIndexReturnsThatPosition() {
        Route route = new Route(List.of(
                Position.of("1"),
                Position.of("2"),
                Position.of("3"),
                Position.of("4")
        ));
        assertEquals(Position.of("2"), route.positionAt(1));
        assertEquals(Position.of("3"), route.positionAt(2));
    }

    @Test
    void endIndexrReturnsLastIndex() {
        Route route = new Route(List.of(
                Position.of("1"),
                Position.of("2"),
                Position.of("3"),
                Position.of("4")
        ));
        assertEquals(3, route.endIndex());
    }
}
