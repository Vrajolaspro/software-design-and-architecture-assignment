package software.design.and.architecture.infrastructure.console;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.usecase.port.DiceRollSource;
import software.design.and.architecture.usecase.port.GamePresenter;
import software.design.and.architecture.usecase.port.GameStore;
import software.design.and.architecture.usecase.service.PlayAndSaveGameUseCase;
import software.design.and.architecture.usecase.service.ReplayGameUseCase;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final PlayAndSaveGameUseCase playAndSave;
    private final ReplayGameUseCase replay;
    private final DiceRollSource diceRollSource;
    private final GamePresenter presenter;
    private final ConsoleGameConfigSelector configSelector;
    private final GameStore gameStore;
    private final Scanner scanner = new Scanner(System.in);
    private static final boolean TEST_SINGLE_CONFIG = true;

    public ConsoleRunner(PlayAndSaveGameUseCase playAndSave, ReplayGameUseCase replay, DiceRollSource diceRollSource, GamePresenter presenter, ConsoleGameConfigSelector configSelector, GameStore gameStore) {
        this.playAndSave = playAndSave;
        this.replay = replay;
        this.diceRollSource = diceRollSource;
        this.presenter = presenter;
        this.configSelector = configSelector;
        this.gameStore = gameStore;
    }

    @Override
    public void run(String... args) {
        if (TEST_SINGLE_CONFIG) {
            GameConfig config = testConfig();
            presenter.showBanner(config);
            presenter.showMessage("Enter roll totals as integers (" +
                    config.diceMode().min() + "-" + config.diceMode().max() +
                    "). Type 'q' to quit.");
            presenter.showMessage("");
            Optional<String> savedId = playAndSave.playAndSave(config, diceRollSource, presenter);
            presenter.showMessage("");
            if (savedId.isPresent()) {
                presenter.showMessage("Saved game id: " + savedId.get());
            } else {
                presenter.showMessage("Game was not saved (did not complete).");
            }
            presenter.showMessage("Goodbye.");
            return;
        }
        while (true) {
            String choice = mainMenu();
            if (choice.equals("q")) {
                presenter.showMessage("Goodbye.");
                return;
            }
            if (choice.equals("1")) {
                playFlow();
            } else if (choice.equals("2")) {
                replayFlow();
            }
        }
    }

    private GameConfig testConfig() {
        return GameConfig.basicTwoPlayer();
        // return GameConfig.exactEndTwoPlayer();
        // return GameConfig.exactEndForfeitOnHitTwoPlayer();
        // return GameConfig.singleDieExactEndForfeitOnHitTwoPlayer();
    }

    private String mainMenu() {
        while (true) {
            presenter.showMessage("=== Simple Frustration ===");
            presenter.showMessage("1) Play (and save when finished)");
            presenter.showMessage("2) Replay a saved game");
            presenter.showMessage("q) Quit");
            System.out.print("Select: ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("1") || input.equals("2") || input.equals("q")) {
                presenter.showMessage("");
                return input;
            }
            presenter.showMessage("Invalid choice.\n");
        }
    }

    private void playFlow() {
        GameConfig config = null;
        while (true) {
            if (config == null) {
                config = configSelector.chooseConfig();
                if (config == null) {
                    presenter.showMessage("Back to menu.\n");
                    return;
                }
            }
            presenter.showBanner(config);
            presenter.showMessage("Enter roll totals as integers (" +
                    config.diceMode().min() + "-" + config.diceMode().max() +
                    "). Type 'q' to quit.");
            presenter.showMessage("");
            Optional<String> savedId = playAndSave.playAndSave(config, diceRollSource, presenter);
            if (savedId.isPresent()) {
                presenter.showMessage("Saved game id: " + savedId.get());
            } else {
                presenter.showMessage("Game was not saved (did not complete).");
            }
            presenter.showMessage("");
            String next = askNextAction();
            if (next.equals("q")) {
                presenter.showMessage("Back to menu.\n");
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

    private void replayFlow() {
        List<String> ids = gameStore.listIds();
        if (ids.isEmpty()) {
            presenter.showMessage("No saved games yet.\n");
            return;
        }
        presenter.showMessage("Saved game ids: " + String.join(", ", ids));
        System.out.print("Enter id to replay (or 'q' to cancel): ");
        String id = scanner.nextLine().trim();
        presenter.showMessage("");
        if (id.equalsIgnoreCase("q")) {
            presenter.showMessage("Back to menu.\n");
            return;
        }
        replay.replay(id, presenter);
        presenter.showMessage("");
    }

    private String askNextAction() {
        while (true) {
            System.out.print("Play again? (y = same settings, n = choose new settings, q = back to menu): ");
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
