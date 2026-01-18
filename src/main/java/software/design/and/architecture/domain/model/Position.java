package software.design.and.architecture.domain.model;

import java.util.Objects;

public final class Position {
    private final String label;

    private Position(String label) {
        this.label = label;
    }

    public static Position of(String label) {
        return new Position(label);
    }

    public String label() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position other)) return false;
        return Objects.equals(label, other.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
