package software.design.and.architecture.domain.model;

public enum DiceMode {
    ONE_DIE(1, 6, "1 die (1-6)"),
    TWO_DICE_TOTAL(2, 12, "2 dice total (2-12)");

    private final int min;
    private final int max;
    private final String display;

    DiceMode(int min, int max, String display) {
        this.min = min;
        this.max = max;
        this.display = display;
    }

    public int min() {
        return min;
    }

    public int max() {
        return max;
    }

    public String display() {
        return display;
    }
}
