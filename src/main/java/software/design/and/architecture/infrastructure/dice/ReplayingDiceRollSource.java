package software.design.and.architecture.infrastructure.dice;

import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.domain.model.PlayerColor;
import software.design.and.architecture.usecase.port.DiceRollSource;

import java.util.List;
import java.util.OptionalInt;

public final class ReplayingDiceRollSource implements DiceRollSource {

    private final List<Integer> rolls;
    private int idx = 0;

    public ReplayingDiceRollSource(List<Integer> rolls) {
        this.rolls = rolls;
    }

    @Override
    public OptionalInt nextRoll(GameConfig config, PlayerColor currentPlayer) {
        if (idx >= rolls.size()) {
            return OptionalInt.empty();
        }
        return OptionalInt.of(rolls.get(idx++));
    }
}
