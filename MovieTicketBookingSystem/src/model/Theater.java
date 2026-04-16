package model;

import java.util.ArrayList;
import java.util.List;

/**
 * MODEL: Theater
 * Represents a cinema hall that contains multiple Seat objects.
 * Seats are organized into 3 classes: BALCONY, GOLD, SILVER.
 * Each class has 5 blocks × 5 rows × 6 columns = 150 seats per class.
 * Total seats per theater = 450.
 *
 * OOP Concept: Encapsulation + Composition
 * (Theater "has" many Seats)
 */
public class Theater {

    // ─────────────────────────────────────────────
    //  CONSTANTS — Seat Layout
    // ─────────────────────────────────────────────

    public static final int BLOCKS_PER_CLASS = 3;   // 5 blocks in each class
    public static final int ROWS_PER_BLOCK   = 5;   // 5 rows per block   (A–E)
    public static final int COLS_PER_ROW     = 5;   // 6 columns per row  (1–6)

    // The 3 seat classes available in every theater
    public static final String[] SEAT_CLASSES = {"BALCONY", "GOLD", "SILVER"};

    // Pricing per class (in ₹)
    public static final int BALCONY_PRICE = 500;
    public static final int GOLD_PRICE    = 300;
    public static final int SILVER_PRICE  = 150;


    // ─────────────────────────────────────────────
    //  FIELDS
    // ─────────────────────────────────────────────

    private String      theaterId;    // Unique ID e.g. "T001"
    private String      theaterName;  // e.g. "PVR Cinemas"
    private String      location;     // e.g. "Hyderabad"
    private List<Seat>  seats;        // All seats in this theater


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    /**
     * Creates a Theater and immediately initializes all its seats.
     * Seat IDs follow the pattern: CLASS-BLOCK-ROW-COL
     * e.g. BALCONY-1-A-1, GOLD-3-C-4, SILVER-5-E-6
     */
    public Theater(String theaterId, String theaterName, String location) {
        this.theaterId   = theaterId;
        this.theaterName = theaterName;
        this.location    = location;
        this.seats       = new ArrayList<>();
        initializeSeats();   // Auto-generate all seats on creation
    }


    // ─────────────────────────────────────────────
    //  SEAT INITIALIZATION
    // ─────────────────────────────────────────────

    /**
     * Generates all seats for this theater.
     * Structure: 3 classes × 5 blocks × 5 rows × 6 cols = 450 seats total.
     *
     * Seat ID format: "BALCONY-2-C-4"
     *  → class=BALCONY, block=2, row=C, col=4
     */
    private void initializeSeats() {
        char[] rowLabels = {'A', 'B', 'C', 'D', 'E'};  // Row labels A to E

        for (String seatClass : SEAT_CLASSES) {
            int price = getPriceForClass(seatClass);

            for (int block = 1; block <= BLOCKS_PER_CLASS; block++) {
                for (char row : rowLabels) {
                    for (int col = 1; col <= COLS_PER_ROW; col++) {

                        // Build unique seat ID e.g. "GOLD-3-B-5"
                        String seatId = seatClass + "-" + block + "-" + row + "-" + col;

                        seats.add(new Seat(seatId, seatClass, block, row, col, price));
                    }
                }
            }
        }
    }

    /**
     * Returns the ticket price for a given seat class.
     */
    public int getPriceForClass(String seatClass) {
        switch (seatClass.toUpperCase()) {
            case "BALCONY": return BALCONY_PRICE;
            case "GOLD":    return GOLD_PRICE;
            default:        return SILVER_PRICE;   // SILVER
        }
    }


    // ─────────────────────────────────────────────
    //  SEAT LOOKUP
    // ─────────────────────────────────────────────

    /**
     * Finds and returns a Seat by its ID.
     * Returns null if not found.
     *
     * @param seatId  e.g. "GOLD-2-B-3"
     */
    public Seat getSeatById(String seatId) {
        for (Seat seat : seats) {
            if (seat.getSeatId().equalsIgnoreCase(seatId)) {
                return seat;
            }
        }
        return null;  // Seat not found
    }

    /**
     * Returns all seats belonging to a specific class AND block.
     * Used by SeatLayoutPrinter to draw one block at a time.
     *
     * @param seatClass  "BALCONY", "GOLD", or "SILVER"
     * @param block      Block number 1–5
     */
    public List<Seat> getSeatsByClassAndBlock(String seatClass, int block) {
        List<Seat> result = new ArrayList<>();
        for (Seat seat : seats) {
            if (seat.getSeatClass().equalsIgnoreCase(seatClass)
                    && seat.getBlock() == block) {
                result.add(seat);
            }
        }
        return result;
    }

    /**
     * Resets ALL seats in this theater back to available (unbooked).
     * Called when a show is reset or for testing purposes.
     */
    public void resetAllSeats() {
        for (Seat seat : seats) {
            seat.setBooked(false);
        }
    }


    // ─────────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────────

    public String     getTheaterId()   { return theaterId; }
    public String     getTheaterName() { return theaterName; }
    public String     getLocation()    { return location; }
    public List<Seat> getSeats()       { return seats; }


    // ─────────────────────────────────────────────
    //  UTILITY
    // ─────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("Theater[%s - %s | Location: %s | Total Seats: %d]",
                theaterId, theaterName, location, seats.size());
    }
}