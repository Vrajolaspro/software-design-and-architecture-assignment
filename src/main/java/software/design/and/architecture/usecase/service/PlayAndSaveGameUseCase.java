package software.design.and.architecture.usecase.service;

import org.springframework.stereotype.Service;
import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.infrastructure.dice.RecordingDiceRollSource;
import software.design.and.architecture.usecase.model.GameConfigSnapshot;
import software.design.and.architecture.usecase.model.GameOutcome;
import software.design.and.architecture.usecase.model.GameRecord;
import software.design.and.architecture.usecase.port.DiceRollSource;
import software.design.and.architecture.usecase.port.GamePresenter;
import software.design.and.architecture.usecase.port.GameStore;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class PlayAndSaveGameUseCase {

    private final PlayGameUseCase playGameUseCase;
    private final GameStore gameStore;

    public PlayAndSaveGameUseCase(PlayGameUseCase playGameUseCase, GameStore gameStore) {
        this.playGameUseCase = playGameUseCase;
        this.gameStore = gameStore;
    }

    public Optional<String> playAndSave(GameConfig config, DiceRollSource dice, GamePresenter presenter) {
        RecordingDiceRollSource recordingDice = new RecordingDiceRollSource(dice);
        GameOutcome outcome = playGameUseCase.playToCompletionWithOutcome(config, recordingDice, presenter);
        if (!outcome.completed()) {
            return Optional.empty();
        }
        List<Integer> rolls = recordingDice.recordedRolls();
        GameRecord record = new GameRecord(
                null,
                Instant.now(),
                GameConfigSnapshot.from(config),
                rolls,
                outcome
        );
        String id = gameStore.save(record);
        return Optional.of(id);
    }
}
