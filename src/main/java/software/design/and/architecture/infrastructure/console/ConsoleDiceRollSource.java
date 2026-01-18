package software.design.and.architecture.infrastructure.console;

import org.springframework.stereotype.Component;
import software.design.and.architecture.domain.model.PlayerColor;
import software.design.and.architecture.usecase.port.DiceRollSource;

import java.util.OptionalInt;
import java.util.Scanner;

@Component
public class ConsoleDiceRollSource implements DiceRollSource {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public OptionalInt nextRoll(PlayerColor currentPlayer) {
        while (true) {
            System.out.print(currentPlayer.displayName() + " roll> ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                return OptionalInt.empty();
            }
            try {
                int roll = Integer.parseInt(input);
                if (roll <= 0) {
                    System.out.println("Enter a positive integer (or 'q' to quit).");
                    continue;
                }
                return OptionalInt.of(roll);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Enter an integer (or 'q' to quit).");
            }
        }
    }
}
