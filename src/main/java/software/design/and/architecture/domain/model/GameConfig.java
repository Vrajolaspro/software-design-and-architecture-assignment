package software.design.and.architecture.domain.model;

import software.design.and.architecture.domain.rules.*;

public final class GameConfig {
    private final int boardPositions;
    private final int tailPositions;
    private final EndRule endRule;
    private final HitRule hitRule;
    private final DiceMode diceMode;
    private final String description;

    private GameConfig(int boardPositions,
                       int tailPositions,
                       EndRule endRule,
                       HitRule hitRule,
                       DiceMode diceMode,
                       String description) {
        this.boardPositions = boardPositions;
        this.tailPositions = tailPositions;
        this.endRule = endRule;
        this.hitRule = hitRule;
        this.diceMode = diceMode;
        this.description = description;
    }

    public static GameConfig basicTwoPlayer() {
        return new GameConfig(
                18, 3,
                new OvershootAllowedEndRule(),
                new AllowHitRule(),
                DiceMode.TWO_DICE_TOTAL,
                "Basic Game (" + DiceMode.TWO_DICE_TOTAL.display() + ", overshoot allowed, hits allowed)"
        );
    }

    public static GameConfig exactEndTwoPlayer() {
        return new GameConfig(
                18, 3,
                new ExactEndRule(),
                new AllowHitRule(),
                DiceMode.TWO_DICE_TOTAL,
                "Variation: Exact End (" + DiceMode.TWO_DICE_TOTAL.display() + ", overshoot forfeits), hits allowed"
        );
    }

    public static GameConfig exactEndForfeitOnHitTwoPlayer() {
        return new GameConfig(
                18, 3,
                new ExactEndRule(),
                new ForfeitOnHitRule(),
                DiceMode.TWO_DICE_TOTAL,
                "Variations: Exact End + Forfeit on HIT (" + DiceMode.TWO_DICE_TOTAL.display() + ")"
        );
    }

    public static GameConfig singleDieExactEndForfeitOnHitTwoPlayer() {
        return new GameConfig(
                18, 3,
                new ExactEndRule(),
                new ForfeitOnHitRule(),
                DiceMode.ONE_DIE,
                "Variations: Single Die + Exact End + Forfeit on HIT (" + DiceMode.ONE_DIE.display() + ")"
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

    public DiceMode diceMode() {
        return diceMode;
    }

    public String description() {
        return description;
    }
}
