package software.design.and.architecture.domain.model;

public enum PlayerColor {
    RED("Red"),
    BLUE("Blue");

    private final String displayName;

    PlayerColor(String displayName) {
        this.displayName = displayName;
    }

    public String displayName() {
        return displayName;
    }
}
