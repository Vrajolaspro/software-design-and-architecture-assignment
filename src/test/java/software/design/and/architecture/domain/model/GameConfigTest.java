package software.design.and.architecture.domain.model;

import org.junit.jupiter.api.Test;
import software.design.and.architecture.domain.rules.AllowHitRule;
import software.design.and.architecture.domain.rules.ExactEndRule;
import software.design.and.architecture.domain.rules.ForfeitOnHitRule;
import software.design.and.architecture.domain.rules.OvershootAllowedEndRule;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameConfigTest {

    @Test
    void ofExactEndRequiredTrueUsesExactEndRule() {
        GameConfig config = GameConfig.of(DiceMode.TWO_DICE_TOTAL, true, false);
        assertInstanceOf(ExactEndRule.class, config.endRule());
    }

    @Test
    void ofExactEndRequiredFalseUsesOvershootAllowedEndRule() {
        GameConfig config = GameConfig.of(DiceMode.TWO_DICE_TOTAL, false, false);
        assertInstanceOf(OvershootAllowedEndRule.class, config.endRule());
    }

    @Test
    void ofForfeitOnHitTrueUsesForfeitOnHitRule() {
        GameConfig config = GameConfig.of(DiceMode.TWO_DICE_TOTAL, false, true);
        assertInstanceOf(ForfeitOnHitRule.class, config.hitRule());
    }

    @Test
    void ofForfeitOnHitFalseUsesAllowHitRule() {
        GameConfig config = GameConfig.of(DiceMode.TWO_DICE_TOTAL, false, false);
        assertInstanceOf(AllowHitRule.class, config.hitRule());
    }

    @Test
    void ofDescriptionContainsExpectedParts() {
        GameConfig config = GameConfig.of(DiceMode.ONE_DIE, true, true);

        String d = config.description();
        assertTrue(d.contains("1 die (1-6)"));
        assertTrue(d.toLowerCase().contains("exact end"));
        assertTrue(d.toLowerCase().contains("forfeit on hit"));
    }

    @Test
    void basicTwoPlayerIsRecognizedAsBasicGameInDescription() {
        GameConfig config = GameConfig.basicTwoPlayer();
        assertTrue(config.description().startsWith("Basic Game"));
    }
}
