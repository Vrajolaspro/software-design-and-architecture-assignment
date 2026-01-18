package software.design.and.architecture.usecase.service;

import org.springframework.stereotype.Service;
import software.design.and.architecture.infrastructure.dice.ReplayingDiceRollSource;
import software.design.and.architecture.usecase.model.GameRecord;
import software.design.and.architecture.usecase.port.GamePresenter;
import software.design.and.architecture.usecase.port.GameStore;

import java.util.Optional;

@Service
public class ReplayGameUseCase {

    private final PlayGameUseCase playGameUseCase;
    private final GameStore store;

    public ReplayGameUseCase(PlayGameUseCase playGameUseCase, GameStore store) {
        this.playGameUseCase = playGameUseCase;
        this.store = store;
    }

    public boolean replay(String id, GamePresenter presenter) {
        Optional<GameRecord> maybe = store.load(id);
        if (maybe.isEmpty()) {
            presenter.showMessage("No saved game found with id: " + id);
            return false;
        }
        GameRecord record = maybe.get();
        presenter.showMessage("=== Replaying saved game id " + record.id() + " ===");
        presenter.showMessage("Saved at: " + record.savedAt());
        presenter.showMessage("Roll count: " + record.rolls().size());
        presenter.showMessage("");
        ReplayingDiceRollSource dice = new ReplayingDiceRollSource(record.rolls());
        playGameUseCase.playToCompletion(record.config().toGameConfig(), dice, presenter);
        presenter.showMessage("");
        presenter.showMessage("Replay complete for id " + record.id());
        return true;
    }
}
