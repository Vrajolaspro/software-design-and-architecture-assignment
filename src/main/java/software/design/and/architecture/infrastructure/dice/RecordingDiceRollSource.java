package software.design.and.architecture.infrastructure.dice;

import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.domain.model.PlayerColor;
import software.design.and.architecture.usecase.port.DiceRollSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

public final class RecordingDiceRollSource implements DiceRollSource {

    private final DiceRollSource delegate;
    private final List<Integer> recorded = new ArrayList<>();

    public RecordingDiceRollSource(DiceRollSource delegate) {
        this.delegate = delegate;
    }

    @Override
    public OptionalInt nextRoll(GameConfig config, PlayerColor currentPlayer) {
        OptionalInt roll = delegate.nextRoll(config, currentPlayer);
        if (roll.isPresent()) {
            recorded.add(roll.getAsInt());
        }
        return roll;
    }

    public List<Integer> recordedRolls() {
        return Collections.unmodifiableList(recorded);
    }
}
