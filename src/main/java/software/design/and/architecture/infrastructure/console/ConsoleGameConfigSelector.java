package software.design.and.architecture.infrastructure.console;

import org.springframework.stereotype.Component;
import software.design.and.architecture.domain.model.DiceMode;
import software.design.and.architecture.domain.model.GameConfig;

import java.util.Scanner;

@Component
public class ConsoleGameConfigSelector {

    private final Scanner scanner = new Scanner(System.in);

    public GameConfig chooseConfig(int playerCount) {
        System.out.println("=== Simple Frustration: Setup ===");
        System.out.println("Players: " + playerCount);
        System.out.println("Choose your variations. Press 'q' at any prompt to quit.\n");
        DiceMode diceMode = chooseDiceMode();
        if (diceMode == null) {
            return null;
        }
        Boolean exactEnd = chooseYesNo("Must land exactly on END to win? (y/n): ");
        if (exactEnd == null) {
            return null;
        }
        Boolean forfeitOnHit = chooseYesNo("Forfeit your turn if you would HIT another player? (y/n): ");
        if (forfeitOnHit == null) {
            return null;
        }
        System.out.println();
        return GameConfig.of(playerCount, diceMode, exactEnd, forfeitOnHit);
    }

    private DiceMode chooseDiceMode() {
        while (true) {
            System.out.println("Dice mode:");
            System.out.println("  1) " + DiceMode.ONE_DIE.display());
            System.out.println("  2) " + DiceMode.TWO_DICE_TOTAL.display());
            System.out.print("Selection (1/2): ");
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                return null;
            }
            if (input.equals("1")) {
                return DiceMode.ONE_DIE;
            }
            if (input.equals("2")) {
                return DiceMode.TWO_DICE_TOTAL;
            }
            System.out.println("Invalid choice. Enter 1 or 2.\n");
        }
    }

    private Boolean chooseYesNo(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (input.equalsIgnoreCase("q")) {
                return null;
            }
            if (input.equalsIgnoreCase("y")) {
                return true;
            }
            if (input.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Please enter 'y' or 'n'.");
        }
    }
}
