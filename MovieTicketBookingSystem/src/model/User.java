package model;

import java.util.ArrayList;
import java.util.List;

/**
 * MODEL: User
 * Represents a registered user of the system.
 * Stores personal info + their booking history (list of booking IDs).
 *
 * OOP Concept: Encapsulation — all fields are private, accessed via getters/setters.
 */
public class User {

    // ─────────────────────────────────────────────
    //  FIELDS
    // ─────────────────────────────────────────────

    private String name;         // Full name of the user
    private String username;     // Unique login ID
    private String password;     // Plain text password (no DB, so kept simple)
    private String email;        // Email address

    // Stores booking IDs made by this user
    // e.g., ["BK001", "BK002"]
    private List<String> bookingIds;


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    /**
     * Creates a new User with all required details.
     * bookingIds list is initialized as empty — gets populated when user books tickets.
     */
    public User(String name, String username, String password, String email) {
        this.name       = name;
        this.username   = username;
        this.password   = password;
        this.email      = email;
        this.bookingIds = new ArrayList<>();  // Start with empty booking history
    }


    // ─────────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────────

    public String getName()     { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail()    { return email; }

    /**
     * Returns the list of booking IDs for this user.
     * BookingService uses this to fetch full booking details.
     */
    public List<String> getBookingIds() { return bookingIds; }


    // ─────────────────────────────────────────────
    //  SETTERS  (only fields that can change)
    // ─────────────────────────────────────────────

    public void setName(String name)         { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setEmail(String email)       { this.email = email; }

    // Username is intentionally NOT settable after creation (acts as unique key)


    // ─────────────────────────────────────────────
    //  UTILITY METHODS
    // ─────────────────────────────────────────────

    /**
     * Adds a new booking ID to this user's history.
     * Called by BookingService after a successful booking.
     *
     * @param bookingId  e.g. "BK001"
     */
    public void addBookingId(String bookingId) {
        bookingIds.add(bookingId);
    }

    /**
     * Removes a booking ID from this user's history.
     * Called by BookingService when a booking is cancelled.
     *
     * @param bookingId  The ID to remove
     */
    public void removeBookingId(String bookingId) {
        bookingIds.remove(bookingId);
    }

    /**
     * Checks if this user has a particular booking.
     * Useful for cancellation validation.
     *
     * @param bookingId  The booking ID to check
     * @return true if the user owns this booking
     */
    public boolean hasBooking(String bookingId) {
        return bookingIds.contains(bookingId);
    }

    /**
     * Returns a clean summary of the user — useful for admin panel display.
     */
    @Override
    public String toString() {
        return String.format(
            "User[ name=%-15s | username=%-12s | email=%-25s | bookings=%d ]",
            name, username, email, bookingIds.size()
        );
    }
}