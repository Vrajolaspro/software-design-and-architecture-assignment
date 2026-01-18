package software.design.and.architecture.infrastructure.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.usecase.port.DiceRollSource;
import software.design.and.architecture.usecase.port.GamePresenter;
import software.design.and.architecture.usecase.service.PlayGameUseCase;

import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final PlayGameUseCase playGameUseCase;
    private final DiceRollSource diceRollSource;
    private final GamePresenter presenter;
    private final ConsoleGameConfigSelector configSelector;
    private final Scanner scanner = new Scanner(System.in);

    public ConsoleRunner(PlayGameUseCase playGameUseCase, DiceRollSource diceRollSource, GamePresenter presenter, ConsoleGameConfigSelector configSelector) {
        this.playGameUseCase = playGameUseCase;
        this.diceRollSource = diceRollSource;
        this.presenter = presenter;
        this.configSelector = configSelector;
    }

    @Override
    public void run(String... args) {
        GameConfig config = null;
        while (true) {
            if (config == null) {
                config = configSelector.chooseConfig();
                if (config == null) {
                    presenter.showMessage("Goodbye.");
                    return;
                }
            }
            presenter.showBanner(config);
            presenter.showMessage("Enter roll totals as integers (" +
                    config.diceMode().min() + "-" + config.diceMode().max() +
                    "). Type 'q' to quit.");
            presenter.showMessage("");
            playGameUseCase.playToCompletion(config, diceRollSource, presenter);
            presenter.showMessage("");
            String next = askNextAction();
            if (next.equals("q")) {
                presenter.showMessage("Goodbye.");
                return;
            }
            if (next.equals("same")) {
                presenter.showMessage("");
                continue;
            }
            if (next.equals("new")) {
                config = null;
                presenter.showMessage("");
            }
        }
    }

    private String askNextAction() {
        while (true) {
            System.out.print("Play again? (y = same settings, n = choose new settings, q = quit): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                return "q";
            }
            if (input.equalsIgnoreCase("y")) {
                return "same";
            }
            if (input.equalsIgnoreCase("n")) {
                return "new";
            }
            System.out.println("Please enter 'y', 'n', or 'q'.");
        }
    }
}
