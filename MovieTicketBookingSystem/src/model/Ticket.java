package model;

import java.util.List;

/**
 * MODEL: Ticket
 * Represents a printable ticket generated after a successful booking.
 * This is the "receipt" shown to the user in the console.
 *
 * OOP Concept: Encapsulation
 * Ticket is a value object — created once, never modified.
 */
public class Ticket {

    // ─────────────────────────────────────────────
    //  FIELDS
    // ─────────────────────────────────────────────

    private String       ticketId;      // e.g. "TK001"
    private String       bookingId;     // Links back to Booking
    private String       username;      // Passenger name
    private String       movieTitle;    // Movie name
    private String       theaterName;   // Theater name
    private String       showDate;      // Show date
    private String       showTime;      // Show time
    private String       seatClass;     // BALCONY / GOLD / SILVER
    private List<String> seatIds;       // All booked seat IDs
    private int          totalAmount;   // Total ₹ paid
    private String       issuedAt;      // Timestamp of ticket issue


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    public Ticket(String ticketId, String bookingId, String username,
                  String movieTitle, String theaterName, String showDate,
                  String showTime, String seatClass, List<String> seatIds,
                  int totalAmount, String issuedAt) {
        this.ticketId     = ticketId;
        this.bookingId    = bookingId;
        this.username     = username;
        this.movieTitle   = movieTitle;
        this.theaterName  = theaterName;
        this.showDate     = showDate;
        this.showTime     = showTime;
        this.seatClass    = seatClass;
        this.seatIds      = seatIds;
        this.totalAmount  = totalAmount;
        this.issuedAt     = issuedAt;
    }


    // ─────────────────────────────────────────────
    //  GETTERS  (no setters — tickets are immutable)
    // ─────────────────────────────────────────────

    public String       getTicketId()    { return ticketId; }
    public String       getBookingId()   { return bookingId; }
    public String       getUsername()    { return username; }
    public String       getMovieTitle()  { return movieTitle; }
    public String       getTheaterName() { return theaterName; }
    public String       getShowDate()    { return showDate; }
    public String       getShowTime()    { return showTime; }
    public String       getSeatClass()   { return seatClass; }
    public List<String> getSeatIds()     { return seatIds; }
    public int          getTotalAmount() { return totalAmount; }
    public String       getIssuedAt()    { return issuedAt; }


    // ─────────────────────────────────────────────
    //  PRINT TICKET  (console receipt)
    // ─────────────────────────────────────────────

    /**
     * Prints a beautifully formatted ticket to the console.
     * Called by TicketService after booking is confirmed.
     */
    public void printTicket() {
        System.out.println("\n");
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║         🎬  BOOKMYSHOW LITE  —  E-TICKET         ║");
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        System.out.printf( "  ║  Ticket ID    : %-33s║%n", ticketId);
        System.out.printf( "  ║  Booking ID   : %-33s║%n", bookingId);
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        System.out.printf( "  ║  Passenger    : %-33s║%n", username);
        System.out.printf( "  ║  Movie        : %-33s║%n", movieTitle);
        System.out.printf( "  ║  Theater      : %-33s║%n", theaterName);
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        System.out.printf( "  ║  Date         : %-33s║%n", showDate);
        System.out.printf( "  ║  Time         : %-33s║%n", showTime);
        System.out.printf( "  ║  Class        : %-33s║%n", seatClass);
        System.out.printf( "  ║  Seats        : %-33s║%n", String.join(", ", seatIds));
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        System.out.printf( "  ║  Amount Paid  : ₹%-32d║%n", totalAmount);
        System.out.printf( "  ║  Issued At    : %-33s║%n", issuedAt);
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        System.out.println("  ║   ✅ BOOKING CONFIRMED — Enjoy Your Movie! 🍿    ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.println();
    }

    @Override
    public String toString() {
        return String.format("Ticket[%s | %s | %s | %s | ₹%d]",
                ticketId, bookingId, movieTitle, showTime, totalAmount);
    }
}