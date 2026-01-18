package software.design.and.architecture.domain.model;

import java.util.List;

public final class Route {
    private final List<Position> path;

    public Route(List<Position> path) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("Route path must not be empty");
        }
        this.path = List.copyOf(path);
    }

    public Position positionAt(int index) {
        if (index <= 0) {
            return path.get(0);
        }
        if (index >= path.size() - 1) {
            return path.get(path.size() - 1);
        }
        return path.get(index);
    }

    public int endIndex() {
        return path.size() - 1;
    }
}
