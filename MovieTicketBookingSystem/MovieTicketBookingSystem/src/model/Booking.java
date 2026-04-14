package model;

import java.util.List;

/**
 * MODEL: Booking
 * Represents a confirmed ticket booking made by a User.
 * Stores all booking details needed for ticket generation and history.
 *
 * OOP Concept: Encapsulation + Association
 * (Booking links User → Movie → Show → Seats)
 */
public class Booking {

    // ─────────────────────────────────────────────
    //  FIELDS
    // ─────────────────────────────────────────────

    private String       bookingId;      // Unique ID e.g. "BK001"
    private String       username;       // Who made the booking
    private String       movieTitle;     // Movie name (stored for easy display)
    private String       showId;         // Which show was booked
    private String       showTime;       // e.g. "07:00 PM"
    private String       showDate;       // e.g. "2025-01-15"
    private String       theaterName;    // e.g. "PVR Cinemas"
    private List<String> seatIds;        // List of booked seat IDs
    private String       seatClass;      // "BALCONY", "GOLD", or "SILVER"
    private int          totalAmount;    // Total cost in ₹
    private String       bookingStatus;  // "CONFIRMED" or "CANCELLED"
    private String       bookingTime;    // When the booking was made


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    /**
     * Creates a new Booking. Status is CONFIRMED by default.
     */
    public Booking(String bookingId, String username, String movieTitle,
                   String showId, String showTime, String showDate,
                   String theaterName, List<String> seatIds,
                   String seatClass, int totalAmount, String bookingTime) {
        this.bookingId     = bookingId;
        this.username      = username;
        this.movieTitle    = movieTitle;
        this.showId        = showId;
        this.showTime      = showTime;
        this.showDate      = showDate;
        this.theaterName   = theaterName;
        this.seatIds       = seatIds;
        this.seatClass     = seatClass;
        this.totalAmount   = totalAmount;
        this.bookingStatus = "CONFIRMED";   // Always starts as confirmed
        this.bookingTime   = bookingTime;
    }


    // ─────────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────────

    public String       getBookingId()     { return bookingId; }
    public String       getUsername()      { return username; }
    public String       getMovieTitle()    { return movieTitle; }
    public String       getShowId()        { return showId; }
    public String       getShowTime()      { return showTime; }
    public String       getShowDate()      { return showDate; }
    public String       getTheaterName()   { return theaterName; }
    public List<String> getSeatIds()       { return seatIds; }
    public String       getSeatClass()     { return seatClass; }
    public int          getTotalAmount()   { return totalAmount; }
    public String       getBookingStatus() { return bookingStatus; }
    public String       getBookingTime()   { return bookingTime; }


    // ─────────────────────────────────────────────
    //  SETTERS
    // ─────────────────────────────────────────────

    /**
     * Updates booking status — used when cancelling.
     * Only valid values: "CONFIRMED" or "CANCELLED"
     */
    public void setBookingStatus(String status) { this.bookingStatus = status; }


    // ─────────────────────────────────────────────
    //  UTILITY
    // ─────────────────────────────────────────────

    /**
     * Prints a compact booking summary row.
     * Used in booking history list view.
     */
    public void printSummary() {
        System.out.printf(
            "  [%-6s]  %-22s | %-10s | %-10s | Seats: %-3d | ₹%-5d | %s%n",
            bookingId,
            movieTitle,
            showDate,
            showTime,
            seatIds.size(),
            totalAmount,
            bookingStatus.equals("CONFIRMED") ? "✅ CONFIRMED" : "❌ CANCELLED"
        );
    }

    /**
     * Prints full booking details.
     * Used for detailed view and ticket generation.
     */
    public void printDetails() {
        System.out.println("\n  ┌─────────────────────────────────────────┐");
        System.out.printf( "  │  Booking ID   : %-26s│%n", bookingId);
        System.out.printf( "  │  Movie        : %-26s│%n", movieTitle);
        System.out.printf( "  │  Show         : %-26s│%n", showId);
        System.out.printf( "  │  Date         : %-26s│%n", showDate);
        System.out.printf( "  │  Time         : %-26s│%n", showTime);
        System.out.printf( "  │  Theater      : %-26s│%n", theaterName);
        System.out.printf( "  │  Seat Class   : %-26s│%n", seatClass);
        System.out.printf( "  │  Seats        : %-26s│%n", String.join(", ", seatIds));
        System.out.printf( "  │  Total Amount : ₹%-25d│%n", totalAmount);
        System.out.printf( "  │  Status       : %-26s│%n", bookingStatus);
        System.out.printf( "  │  Booked At    : %-26s│%n", bookingTime);
        System.out.println("  └─────────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("Booking[%s | %s | %s | %s | ₹%d | %s]",
                bookingId, username, movieTitle, showTime, totalAmount, bookingStatus);
    }
}