package software.design.and.architecture.domain.model;

import org.junit.jupiter.api.Test;
import software.design.and.architecture.domain.rules.*;

import static org.junit.jupiter.api.Assertions.*;

class GameConfigTest {

    @Test
    void of_exactEndRequiredTrue_usesExactEndRule() {
        GameConfig config = GameConfig.of(DiceMode.TWO_DICE_TOTAL, true, false);
        assertInstanceOf(ExactEndRule.class, config.endRule());
    }

    @Test
    void of_exactEndRequiredFalse_usesOvershootAllowedEndRule() {
        GameConfig config = GameConfig.of(DiceMode.TWO_DICE_TOTAL, false, false);
        assertInstanceOf(OvershootAllowedEndRule.class, config.endRule());
    }

    @Test
    void of_forfeitOnHitTrue_usesForfeitOnHitRule() {
        GameConfig config = GameConfig.of(DiceMode.TWO_DICE_TOTAL, false, true);
        assertInstanceOf(ForfeitOnHitRule.class, config.hitRule());
    }

    @Test
    void of_forfeitOnHitFalse_usesAllowHitRule() {
        GameConfig config = GameConfig.of(DiceMode.TWO_DICE_TOTAL, false, false);
        assertInstanceOf(AllowHitRule.class, config.hitRule());
    }

    @Test
    void of_descriptionContainsExpectedParts() {
        GameConfig config = GameConfig.of(DiceMode.ONE_DIE, true, true);

        String d = config.description();
        assertTrue(d.contains("1 die (1-6)"));
        assertTrue(d.toLowerCase().contains("exact end"));
        assertTrue(d.toLowerCase().contains("forfeit on hit"));
    }

    @Test
    void basicTwoPlayer_isRecognizedAsBasicGameInDescription() {
        GameConfig config = GameConfig.basicTwoPlayer();
        assertTrue(config.description().startsWith("Basic Game"));
    }
}
