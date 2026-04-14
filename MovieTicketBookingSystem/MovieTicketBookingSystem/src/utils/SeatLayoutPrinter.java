package utils;

import model.Seat;
import model.Theater;

import java.util.List;

/**
 * UTILITY: SeatLayoutPrinter
 * Prints the visual seat map for a Theater in the console.
 *
 * Layout:
 *   - 3 classes: BALCONY (₹500), GOLD (₹300), SILVER (₹150)
 *   - Each class has 5 blocks
 *   - Each block has 5 rows (A–E) × 6 columns (1–6)
 *   - A = Already booked   U = Available
 *
 * OOP Concept: Utility class — pure static methods, no state.
 */
public class SeatLayoutPrinter {

    /**
     * Prints the full seat layout for all 3 classes.
     * Called before the user selects seats during booking.
     *
     * @param theater  The Theater whose seats to display
     */
    public static void printFullLayout(Theater theater) {
        System.out.println("\n  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║             🎭  SEAT LAYOUT                       ║");
        System.out.println("  ║       A = Booked    U = Available                 ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");

        // Print each class section
        printClassLayout(theater, "BALCONY", Theater.BALCONY_PRICE);
        printClassLayout(theater, "GOLD",    Theater.GOLD_PRICE);
        printClassLayout(theater, "SILVER",  Theater.SILVER_PRICE);
    }


    // ─────────────────────────────────────────────
    //  PRINT ONE CLASS  (e.g. all BALCONY blocks)
    // ─────────────────────────────────────────────

    /**
     * Prints all 5 blocks for a given seat class.
     *
     * @param theater    The theater
     * @param className  "BALCONY", "GOLD", or "SILVER"
     * @param price      Price per seat for this class
     */
    private static void printClassLayout(Theater theater, String className, int price) {
        System.out.println("\n  ┌──────────────────────────────────────────────────┐");
        System.out.printf( "  │  🏷️  %-10s CLASS  —  ₹%d per seat             │%n",
                className, price);
        System.out.println("  └──────────────────────────────────────────────────┘");

        // Print all 5 blocks side by side (2 per row for readability)
        for (int block = 1; block <= Theater.BLOCKS_PER_CLASS; block++) {
            printBlock(theater, className, block);
        }
    }


    // ─────────────────────────────────────────────
    //  PRINT ONE BLOCK  (5 rows × 6 cols)
    // ─────────────────────────────────────────────

    /**
     * Prints a single block with row/column labels.
     *
     * Example output:
     *
     *    BLOCK 2
     *       1  2  3  4  5  6
     *    A [U][U][A][U][U][U]
     *    B [U][A][U][U][U][U]
     *    ...
     *
     * @param theater    The theater containing seats
     * @param className  The seat class
     * @param block      Block number 1–5
     */
    private static void printBlock(Theater theater, String className, int block) {
        List<Seat> blockSeats = theater.getSeatsByClassAndBlock(className, block);

        System.out.println();
        System.out.println("     ┌─── BLOCK " + block + " ───────────────┐");

        // Column header: 1  2  3  4  5  6
        System.out.print("     │    ");
        for (int col = 1; col <= Theater.COLS_PER_ROW; col++) {
            System.out.printf(" %d ", col);
        }
        System.out.println("   │");

        System.out.println("     │   ──────────────────  │");

        // Print each row A through E
        char[] rows = {'A', 'B', 'C', 'D', 'E'};
        for (char row : rows) {
            System.out.printf("     │ %c  ", row);   // Row label

            for (int col = 1; col <= Theater.COLS_PER_ROW; col++) {
                // Find this specific seat
                Seat seat = findSeat(blockSeats, row, col);
                if (seat != null) {
                    // [U] or [A] with color cue via brackets
                    System.out.printf("[%s]", seat.getDisplaySymbol());
                } else {
                    System.out.print("[?]");   // Should never happen
                }
            }

            System.out.println("   │");
        }

        System.out.println("     └──────────────────────────┘");
    }


    // ─────────────────────────────────────────────
    //  FIND SEAT IN LIST
    // ─────────────────────────────────────────────

    /**
     * Finds a specific seat from a list by row and column.
     * Helper used inside printBlock.
     *
     * @param seats  List of seats (for one block)
     * @param row    Row label e.g. 'B'
     * @param col    Column number e.g. 3
     * @return       Matching Seat or null
     */
    private static Seat findSeat(List<Seat> seats, char row, int col) {
        for (Seat seat : seats) {
            if (seat.getRow() == row && seat.getCol() == col) {
                return seat;
            }
        }
        return null;
    }
}