package software.design.and.architecture.infrastructure.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.usecase.port.DiceRollSource;
import software.design.and.architecture.usecase.port.GamePresenter;
import software.design.and.architecture.usecase.service.PlayGameUseCase;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final PlayGameUseCase playGameUseCase;
    private final DiceRollSource diceRollSource;
    private final GamePresenter presenter;

    public ConsoleRunner(PlayGameUseCase playGameUseCase,
                         DiceRollSource diceRollSource,
                         GamePresenter presenter) {
        this.playGameUseCase = playGameUseCase;
        this.diceRollSource = diceRollSource;
        this.presenter = presenter;
    }

    @Override
    public void run(String... args) {
        GameConfig config = GameConfig.basicTwoPlayer();
        presenter.showBanner(config);
        presenter.showMessage("Enter roll totals as integers (e.g., 2-12). Type 'q' to quit.");
        presenter.showMessage("");
        playGameUseCase.playToCompletion(config, diceRollSource, presenter);
    }
}
