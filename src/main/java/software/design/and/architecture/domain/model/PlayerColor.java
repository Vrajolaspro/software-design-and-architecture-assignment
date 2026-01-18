package software.design.and.architecture.domain.model;

public enum PlayerColor {
    RED("Red", "R"),
    BLUE("Blue", "B"),
    GREEN("Green", "G"),
    YELLOW("Yellow", "Y");

    private final String displayName;
    private final String tailPrefix;

    PlayerColor(String displayName, String tailPrefix) {
        this.displayName = displayName;
        this.tailPrefix = tailPrefix;
    }

    public String displayName() {
        return displayName;
    }

    public String tailPrefix() {
        return tailPrefix;
    }
}
