package model;

/**
 * MODEL: Show
 * Represents a specific screening of a movie at a theater.
 * Each Show is linked to one Movie and one Theater.
 *
 * OOP Concept: Encapsulation + Association
 * (Show "belongs to" a Movie and "is held at" a Theater)
 */
public class Show {

    // ─────────────────────────────────────────────
    //  FIELDS
    // ─────────────────────────────────────────────

    private String showId;       // Unique ID e.g. "SH001"
    private String movieId;      // Links back to the Movie e.g. "M001"
    private String theaterId;    // Links to the Theater e.g. "T001"
    private String theaterName;  // Stored for quick display (avoid extra lookups)
    private String showTime;     // e.g. "10:00 AM", "02:30 PM", "07:00 PM"
    private String showDate;     // e.g. "2025-01-15"
    private boolean isActive;    // Admin can deactivate a show


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    /**
     * Creates a new Show with all required details.
     * isActive defaults to true — show is live when created.
     */
    public Show(String showId, String movieId, String theaterId,
                String theaterName, String showTime, String showDate) {
        this.showId      = showId;
        this.movieId     = movieId;
        this.theaterId   = theaterId;
        this.theaterName = theaterName;
        this.showTime    = showTime;
        this.showDate    = showDate;
        this.isActive    = true;   // Active by default
    }


    // ─────────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────────

    public String  getShowId()      { return showId; }
    public String  getMovieId()     { return movieId; }
    public String  getTheaterId()   { return theaterId; }
    public String  getTheaterName() { return theaterName; }
    public String  getShowTime()    { return showTime; }
    public String  getShowDate()    { return showDate; }
    public boolean isActive()       { return isActive; }


    // ─────────────────────────────────────────────
    //  SETTERS  (only updatable fields)
    // ─────────────────────────────────────────────

    public void setShowTime(String showTime) { this.showTime = showTime; }
    public void setShowDate(String showDate) { this.showDate = showDate; }
    public void setActive(boolean active)    { this.isActive = active; }


    // ─────────────────────────────────────────────
    //  UTILITY
    // ─────────────────────────────────────────────

    /**
     * Prints a single-line show summary.
     * Used when listing shows for a movie during booking.
     */
    public void printSummary(int index) {
        System.out.printf(
            "  [%d] ShowID: %-6s | Date: %-12s | Time: %-10s | Theater: %-15s | Status: %s%n",
            index,
            showId,
            showDate,
            showTime,
            theaterName,
            isActive ? "✅ Active" : "❌ Inactive"
        );
    }

    @Override
    public String toString() {
        return String.format("Show[%s | Movie:%s | %s %s | Theater:%s]",
                showId, movieId, showDate, showTime, theaterName);
    }
}