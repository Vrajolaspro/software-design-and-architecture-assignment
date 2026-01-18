package software.design.and.architecture.domain.model;

import software.design.and.architecture.domain.rules.*;

public final class GameConfig {
    private final int boardPositions;
    private final int tailPositions;
    private final EndRule endRule;
    private final HitRule hitRule;
    private final String description;

    private GameConfig(int boardPositions, int tailPositions, EndRule endRule, HitRule hitRule, String description) {
        this.boardPositions = boardPositions;
        this.tailPositions = tailPositions;
        this.endRule = endRule;
        this.hitRule = hitRule;
        this.description = description;
    }

    public static GameConfig basicTwoPlayer() {
        return new GameConfig(
                18, 3,
                new OvershootAllowedEndRule(),
                new AllowHitRule(),
                "Basic Game (2 dice totals, overshoot allowed, hits allowed)"
        );
    }

    public static GameConfig exactEndTwoPlayer() {
        return new GameConfig(
                18, 3,
                new ExactEndRule(),
                new AllowHitRule(),
                "Variation: Exact End (overshoot forfeits), hits allowed"
        );
    }

    public static GameConfig exactEndForfeitOnHitTwoPlayer() {
        return new GameConfig(
                18, 3,
                new ExactEndRule(),
                new ForfeitOnHitRule(),
                "Variations: Exact End + Forfeit on HIT"
        );
    }

    public int boardPositions() {
        return boardPositions;
    }

    public int tailPositions() {
        return tailPositions;
    }

    public EndRule endRule() {
        return endRule;
    }

    public HitRule hitRule() {
        return hitRule;
    }

    public String description() {
        return description;
    }
}
