package model;

/**
 * MODEL: Seat
 * Represents a single seat inside a Theater.
 * Tracks whether it is booked or available.
 *
 * OOP Concept: Encapsulation
 * Each seat knows its own class, block, row, column, price, and status.
 */
public class Seat {

    // ─────────────────────────────────────────────
    //  FIELDS
    // ─────────────────────────────────────────────

    private String  seatId;      // Unique ID e.g. "GOLD-2-B-3"
    private String  seatClass;   // "BALCONY", "GOLD", or "SILVER"
    private int     block;       // Block number 1–5
    private char    row;         // Row label A–E
    private int     col;         // Column number 1–6
    private int     price;       // Price in ₹ based on class
    private boolean isBooked;    // true = taken, false = available


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    /**
     * Creates a Seat. All seats start as unbooked (available).
     */
    public Seat(String seatId, String seatClass, int block,
                char row, int col, int price) {
        this.seatId    = seatId;
        this.seatClass = seatClass;
        this.block     = block;
        this.row       = row;
        this.col       = col;
        this.price     = price;
        this.isBooked  = false;   // Available by default
    }


    // ─────────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────────

    public String  getSeatId()    { return seatId; }
    public String  getSeatClass() { return seatClass; }
    public int     getBlock()     { return block; }
    public char    getRow()       { return row; }
    public int     getCol()       { return col; }
    public int     getPrice()     { return price; }
    public boolean isBooked()     { return isBooked; }


    // ─────────────────────────────────────────────
    //  SETTERS
    // ─────────────────────────────────────────────

    /**
     * Marks this seat as booked (true) or available (false).
     * Called by BookingService during booking and cancellation.
     */
    public void setBooked(boolean booked) { this.isBooked = booked; }


    // ─────────────────────────────────────────────
    //  UTILITY
    // ─────────────────────────────────────────────

    /**
     * Returns display symbol for seat layout visualization.
     * "A" = Already booked (taken)
     * "U" = Available (unbooked)
     */
    public String getDisplaySymbol() {
        return isBooked ? "A" : "U";
    }

    @Override
    public String toString() {
        return String.format("Seat[%s | %s | Block:%d | Row:%c | Col:%d | ₹%d | %s]",
                seatId, seatClass, block, row, col, price,
                isBooked ? "BOOKED" : "AVAILABLE");
    }
}