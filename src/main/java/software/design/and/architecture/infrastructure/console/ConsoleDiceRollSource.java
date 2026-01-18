package software.design.and.architecture.infrastructure.console;

import org.springframework.stereotype.Component;
import software.design.and.architecture.domain.model.GameConfig;
import software.design.and.architecture.domain.model.PlayerColor;
import software.design.and.architecture.usecase.port.DiceRollSource;

import java.util.OptionalInt;
import java.util.Scanner;

@Component
public class ConsoleDiceRollSource implements DiceRollSource {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public OptionalInt nextRoll(GameConfig config, PlayerColor currentPlayer) {
        int min = config.diceMode().min();
        int max = config.diceMode().max();
        while (true) {
            System.out.print(currentPlayer.displayName() + " roll> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                return OptionalInt.empty();
            }
            try {
                int roll = Integer.parseInt(input);
                if (roll < min || roll > max) {
                    System.out.printf("Enter a value between %d and %d (or 'q' to quit).%n", min, max);
                    continue;
                }
                return OptionalInt.of(roll);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Enter an integer (or 'q' to quit).");
            }
        }
    }
}
