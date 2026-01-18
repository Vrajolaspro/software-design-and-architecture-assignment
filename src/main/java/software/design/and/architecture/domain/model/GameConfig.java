package software.design.and.architecture.domain.model;

import software.design.and.architecture.domain.rules.*;

public final class GameConfig {
    private final int boardPositions;
    private final int tailPositions;
    private final int playerCount;
    private final boolean exactEndRequired;
    private final boolean forfeitOnHit;
    private final EndRule endRule;
    private final HitRule hitRule;
    private final DiceMode diceMode;
    private final String description;

    private GameConfig(int boardPositions, int tailPositions, int playerCount, boolean exactEndRequired, boolean forfeitOnHit, EndRule endRule, HitRule hitRule, DiceMode diceMode, String description) {
        this.boardPositions = boardPositions;
        this.tailPositions = tailPositions;
        this.playerCount = playerCount;
        this.exactEndRequired = exactEndRequired;
        this.forfeitOnHit = forfeitOnHit;
        this.endRule = endRule;
        this.hitRule = hitRule;
        this.diceMode = diceMode;
        this.description = description;
    }

    public static GameConfig basicTwoPlayer() {
        return of(2, DiceMode.TWO_DICE_TOTAL, false, false);
    }

    public static GameConfig exactEndTwoPlayer() {
        return of(2, DiceMode.TWO_DICE_TOTAL, true, false);
    }

    public static GameConfig exactEndForfeitOnHitTwoPlayer() {
        return of(2, DiceMode.TWO_DICE_TOTAL, true, true);
    }

    public static GameConfig singleDieExactEndForfeitOnHitTwoPlayer() {
        return of(2, DiceMode.ONE_DIE, true, true);
    }

    public static GameConfig of(DiceMode diceMode, boolean exactEndRequired, boolean forfeitOnHit) {
        return of(2, diceMode, exactEndRequired, forfeitOnHit);
    }

    public static GameConfig of(int playerCount, DiceMode diceMode, boolean exactEndRequired, boolean forfeitOnHit) {
        if (playerCount != 2 && playerCount != 4) {
            throw new IllegalArgumentException("playerCount must be 2 or 4");
        }
        EndRule endRule = exactEndRequired ? new ExactEndRule() : new OvershootAllowedEndRule();
        HitRule hitRule = forfeitOnHit ? new ForfeitOnHitRule() : new AllowHitRule();
        String description = buildDescription(playerCount, diceMode, exactEndRequired, forfeitOnHit);
        return new GameConfig(18, 3, playerCount, exactEndRequired, forfeitOnHit, endRule, hitRule, diceMode, description);
    }

    private static String buildDescription(int playerCount, DiceMode diceMode, boolean exactEndRequired, boolean forfeitOnHit) {
        String endText = exactEndRequired ? "exact end (overshoot forfeits)" : "overshoot allowed";
        String hitText = forfeitOnHit ? "forfeit on HIT" : "hits allowed";
        boolean isBasic = playerCount == 2 && diceMode == DiceMode.TWO_DICE_TOTAL && !exactEndRequired && !forfeitOnHit;
        String type = isBasic ? "Basic Game" : "Custom Game";
        return type + " (" + playerCount + " players, " + diceMode.display() + ", " + endText + ", " + hitText + ")";
    }

    public int boardPositions() {
        return boardPositions;
    }

    public int tailPositions() {
        return tailPositions;
    }

    public int playerCount() {
        return playerCount;
    }

    public boolean exactEndRequired() {
        return exactEndRequired;
    }

    public boolean forfeitOnHit() {
        return forfeitOnHit;
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
