package utils;

import model.Seat;
import model.Theater;

import java.util.List;

/**
 * UTILITY: SeatLayoutPrinter
 * Prints all 5 blocks of each seat class SIDE BY SIDE,
 * so it looks like an actual theater seating chart.
 *
 * Layout per class:
 *
 *   BLOCK 1          BLOCK 2          BLOCK 3          BLOCK 4          BLOCK 5
 *    1  2  3  4  5  6  |  1  2  3  4  5  6  |  1  2  3  4  5  6  | ...
 * A [U][U][U][U][U][U] | [U][U][U][U][U][U] | [U][U][U][U][U][U] | ...
 * B [U][A][U][U][U][U] | [U][U][U][U][U][U] | [U][U][A][U][U][U] | ...
 * ...
 */
public class SeatLayoutPrinter {

    // Number of columns per block (1–6)
    private static final int COLS = Theater.COLS_PER_ROW;

    // Row labels A–E
    private static final char[] ROWS = {'A', 'B', 'C', 'D', 'E'};


    // ─────────────────────────────────────────────
    //  PUBLIC ENTRY POINT
    // ─────────────────────────────────────────────

    /**
     * Prints seat layout for all 3 classes, each with 5 blocks side by side.
     * Call this before the user selects seats during booking.
     *
     * @param theater  The theater whose layout to print
     */
    public static void printFullLayout(Theater theater) {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("  ║                        🎭  THEATER SEAT LAYOUT                                  ║");
        System.out.println("  ║                    [ U = Available ]  [ A = Booked ]                            ║");
        System.out.println("  ╚══════════════════════════════════════════════════════════════════════════════════╝");

        // ── SCREEN banner at the top ──
        System.out.println();
        System.out.println("  ████████████████████████████  SCREEN  ████████████████████████████");
        System.out.println();

        // Print each class section
        printClassSideBySide(theater, "BALCONY", Theater.BALCONY_PRICE);
        printClassSideBySide(theater, "GOLD",    Theater.GOLD_PRICE);
        printClassSideBySide(theater, "SILVER",  Theater.SILVER_PRICE);

        // ── Legend ──
        System.out.println();
        System.out.println("  ──────────────────────────────────────────────────────────────────────────────────");
        System.out.println("  Seat ID Format:  CLASS-BLOCK-ROW-COL    Example:  GOLD-3-B-4");
        System.out.println("  ──────────────────────────────────────────────────────────────────────────────────");
    }


    // ─────────────────────────────────────────────
    //  PRINT ALL 5 BLOCKS SIDE BY SIDE FOR ONE CLASS
    // ─────────────────────────────────────────────

    /**
     * Prints all 5 blocks of one seat class in a single wide row.
     * Each block shares the same row lines, printed left to right.
     *
     * @param theater    The theater
     * @param className  "BALCONY", "GOLD", or "SILVER"
     * @param price      Price per seat for this class
     */
    private static void printClassSideBySide(Theater theater, String className, int price) {

        // ── Class header ──
        System.out.println();
        System.out.println("  ┌──────────────────────────────────────────────────────────────────────────────────┐");
        System.out.printf( "  │  🏷️  %-10s CLASS  —  ₹%d per seat%47s│%n", className, price, "");
        System.out.println("  └──────────────────────────────────────────────────────────────────────────────────┘");
        System.out.println();

        // ── Pre-fetch all blocks into a 2D list for easy row-by-row access ──
        // blockData[b] = list of seats in block (b+1), ordered by row then col
        List<Seat>[] blockData = new List[Theater.BLOCKS_PER_CLASS];
        for (int b = 0; b < Theater.BLOCKS_PER_CLASS; b++) {
            blockData[b] = theater.getSeatsByClassAndBlock(className, b + 1);
        }

        // ── Block number header line ──
        // e.g.  "       BLOCK 1               BLOCK 2          ..."
        System.out.print("     ");
        for (int b = 1; b <= Theater.BLOCKS_PER_CLASS; b++) {
            // Each block takes: 3 chars per col × 6 cols = 18 chars wide
            // + 2 chars for row label + gap = ~22 chars per block
            System.out.printf("  %-20s", "   BLOCK " + b);
            if (b < Theater.BLOCKS_PER_CLASS) System.out.print("   ");  // gap between blocks
        }
        System.out.println();

        // ── Column number header line ──
        // e.g.  "      1  2  3  4  5  6    |    1  2  3  4  5  6    | ..."
        System.out.print("     ");
        for (int b = 0; b < Theater.BLOCKS_PER_CLASS; b++) {
            System.out.print("   ");                      // indent before cols
            for (int col = 1; col <= COLS; col++) {
                System.out.printf(" %d ", col);           // " 1  2  3  4  5  6 "
            }
            if (b < Theater.BLOCKS_PER_CLASS - 1) {
                System.out.print("  ║");                  // vertical divider between blocks
            }
        }
        System.out.println();

        // ── Separator under column headers ──
        System.out.print("     ");
        for (int b = 0; b < Theater.BLOCKS_PER_CLASS; b++) {
            System.out.print("  ──────────────────");     // underline for 6 cols
            if (b < Theater.BLOCKS_PER_CLASS - 1) {
                System.out.print("  ║");
            }
        }
        System.out.println();

        // ── Print each row (A through E) across all blocks ──
        for (char row : ROWS) {
            System.out.printf("  %c  ", row);             // Row label on the left

            for (int b = 0; b < Theater.BLOCKS_PER_CLASS; b++) {
                System.out.print("  ");                   // left padding per block

                // Print all 6 columns for this row in this block
                for (int col = 1; col <= COLS; col++) {
                    Seat seat = findSeat(blockData[b], row, col);
                    if (seat != null) {
                        // Color-code: booked = bright look with 'A', free = 'U'
                        System.out.printf("[%s]", seat.getDisplaySymbol());
                    } else {
                        System.out.print("[?]");           // Should never happen
                    }
                }

                // Vertical divider between blocks (not after last block)
                if (b < Theater.BLOCKS_PER_CLASS - 1) {
                    System.out.print("  ║");
                }
            }

            System.out.println();  // End of this row line
        }

        // ── Bottom border for this class section ──
        System.out.println();
        System.out.print("     ");
        for (int b = 0; b < Theater.BLOCKS_PER_CLASS; b++) {
            System.out.print("  ══════════════════");
            if (b < Theater.BLOCKS_PER_CLASS - 1) {
                System.out.print("  ╬");
            }
        }
        System.out.println();
    }


    // ─────────────────────────────────────────────
    //  HELPER: Find a seat by row + col in a block's list
    // ─────────────────────────────────────────────

    /**
     * Finds a specific seat from a block's seat list by row and column.
     *
     * @param seats  All seats in one block
     * @param row    Row label e.g. 'C'
     * @param col    Column number e.g. 4
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