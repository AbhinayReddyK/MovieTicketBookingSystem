package utils;

import java.util.Scanner;

/**
 * UTILITY: InputHelper
 * Provides safe input reading methods with validation and error handling.
 * Prevents crashes from invalid input like letters when numbers are expected.
 *
 * OOP Concept: Utility class — all static methods.
 */
public class InputHelper {

    // ─────────────────────────────────────────────
    //  GET INTEGER INPUT  (with validation loop)
    // ─────────────────────────────────────────────

    /**
     * Keeps asking for input until the user enters a valid integer.
     * Prevents InputMismatchException from crashing the program.
     *
     * @param scanner  Shared Scanner instance
     * @param prompt   Message to display e.g. "Enter choice: "
     * @return         A valid integer entered by the user
     */
    public static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Integer.parseInt(input);   // Try converting to int
            } catch (NumberFormatException e) {
                // User typed something that isn't a number
                System.out.println("  ⚠️  Please enter a valid number.");
            }
        }
    }


    // ─────────────────────────────────────────────
    //  GET INTEGER IN RANGE  (bounded input)
    // ─────────────────────────────────────────────

    /**
     * Like getIntInput but also validates that the number is within [min, max].
     * Keeps looping until a valid in-range integer is entered.
     *
     * @param scanner  Shared Scanner instance
     * @param prompt   Message to display
     * @param min      Minimum allowed value (inclusive)
     * @param max      Maximum allowed value (inclusive)
     * @return         A valid integer within the specified range
     */
    public static int getIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            int value = getIntInput(scanner, prompt);

            if (value >= min && value <= max) {
                return value;
            } else {
                System.out.printf("  ⚠️  Please enter a number between %d and %d.%n", min, max);
            }
        }
    }


    // ─────────────────────────────────────────────
    //  GET NON-EMPTY STRING
    // ─────────────────────────────────────────────

    /**
     * Reads a non-empty string from the user.
     * Keeps asking if the user just presses Enter without typing anything.
     *
     * @param scanner  Shared Scanner instance
     * @param prompt   Message to display
     * @return         A non-empty trimmed string
     */
    public static String getNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("  ⚠️  Input cannot be empty. Please try again.");
            }
        }
    }


    // ─────────────────────────────────────────────
    //  GET DOUBLE INPUT
    // ─────────────────────────────────────────────

    /**
     * Reads a valid double (decimal number) from the user.
     * Used for movie rating input in admin panel.
     *
     * @param scanner  Shared Scanner instance
     * @param prompt   Message to display
     * @return         A valid double value
     */
    public static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("  ⚠️  Please enter a valid decimal number (e.g. 8.5).");
            }
        }
    }


    // ─────────────────────────────────────────────
    //  YES / NO CONFIRMATION
    // ─────────────────────────────────────────────

    /**
     * Asks user a yes/no question.
     * Accepts "y", "yes", "n", "no" (case-insensitive).
     *
     * @param scanner  Shared Scanner instance
     * @param prompt   Question to ask e.g. "Confirm booking? (y/n): "
     * @return         true if yes, false if no
     */
    public static boolean getYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) return true;
            if (input.equals("n") || input.equals("no"))  return false;

            System.out.println("  ⚠️  Please enter 'y' for yes or 'n' for no.");
        }
    }
}