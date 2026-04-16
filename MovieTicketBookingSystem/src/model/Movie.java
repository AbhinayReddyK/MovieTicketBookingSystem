package model;

import java.util.ArrayList;
import java.util.List;

/**
 * MODEL: Movie
 * Represents a movie in the system.
 * Stores all movie metadata + its list of shows + user reviews.
 *
 * OOP Concept: Encapsulation + Composition
 * (Movie "has" a list of Shows and a list of Reviews)
 */
public class Movie {

    // ─────────────────────────────────────────────
    //  FIELDS
    // ─────────────────────────────────────────────

    private String       movieId;      // Unique ID e.g. "M001"
    private String       title;        // Movie title e.g. "Inception"
    private String       language;     // e.g. "English", "Hindi", "Telugu"
    private String       genre;        // e.g. "Thriller", "Comedy", "Action"
    private int          duration;     // Duration in minutes e.g. 148
    private double       rating;       // Average rating out of 10.0
    private String       description;  // Short plot summary

    // List of Show objects linked to this movie
    private List<Show>   shows;

    // List of Review objects submitted by users
    private List<Review> reviews;


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    /**
     * Creates a Movie with all core details.
     * Shows and Reviews lists start empty and are populated later.
     */
    public Movie(String movieId, String title, String language,
                 String genre, int duration, double rating, String description) {
        this.movieId     = movieId;
        this.title       = title;
        this.language    = language;
        this.genre       = genre;
        this.duration    = duration;
        this.rating      = rating;
        this.description = description;
        this.shows       = new ArrayList<>();   // No shows yet
        this.reviews     = new ArrayList<>();   // No reviews yet
    }


    // ─────────────────────────────────────────────
    //  GETTERS
    // ─────────────────────────────────────────────

    public String       getMovieId()     { return movieId; }
    public String       getTitle()       { return title; }
    public String       getLanguage()    { return language; }
    public String       getGenre()       { return genre; }
    public int          getDuration()    { return duration; }
    public double       getRating()      { return rating; }
    public String       getDescription() { return description; }
    public List<Show>   getShows()       { return shows; }
    public List<Review> getReviews()     { return reviews; }


    // ─────────────────────────────────────────────
    //  SETTERS  (only updatable fields)
    // ─────────────────────────────────────────────

    public void setRating(double rating)         { this.rating = rating; }
    public void setDescription(String desc)      { this.description = desc; }

    // movieId is intentionally NOT settable after creation


    // ─────────────────────────────────────────────
    //  UTILITY METHODS
    // ─────────────────────────────────────────────

    /**
     * Adds a Show to this movie's show list.
     * Called by AdminService / TheaterService when scheduling shows.
     *
     * @param show  The Show object to link to this movie
     */
    public void addShow(Show show) {
        shows.add(show);
    }

    /**
     * Adds a Review to this movie's review list.
     * Called by BookingService or a future ReviewService.
     *
     * @param review  The Review object to attach
     */
    public void addReview(Review review) {
        reviews.add(review);
    }

    /**
     * Returns a formatted duration string.
     * e.g. 148 minutes → "2h 28m"
     */
    public String getFormattedDuration() {
        int hours   = duration / 60;
        int minutes = duration % 60;
        return hours + "h " + minutes + "m";
    }

    /**
     * Prints a short one-line summary — used in movie list display.
     * e.g.  [M001]  Inception          | English  | Thriller | 2h 28m | ⭐ 9.0
     */
    public void printSummary() {
        System.out.printf(
            "  [%-4s]  %-22s | %-8s | %-10s | %-7s | ⭐ %.1f%n",
            movieId, title, language, genre, getFormattedDuration(), rating
        );
    }

    /**
     * Prints full movie details — used in "View Movie Details" screen.
     */
    public void printDetails() {
        System.out.println("\n╔══════════════════════════════════════════════╗");
        System.out.printf( "║  🎬  %-41s║%n", title);
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println("  Movie ID    : " + movieId);
        System.out.println("  Language    : " + language);
        System.out.println("  Genre       : " + genre);
        System.out.println("  Duration    : " + getFormattedDuration());
        System.out.printf( "  Rating      : ⭐ %.1f / 10.0%n", rating);
        System.out.println("  Description : " + description);

        // ── Show available show timings ──
        System.out.println("\n  📅 Available Shows:");
        if (shows.isEmpty()) {
            System.out.println("     No shows scheduled yet.");
        } else {
            for (int i = 0; i < shows.size(); i++) {
                Show s = shows.get(i);
                System.out.printf(
                    "     [%d] ShowID: %-6s | Time: %-10s | Theater: %s%n",
                    i + 1,
                    s.getShowId(),
                    s.getShowTime(),
                    s.getTheaterName()
                );
            }
        }

        // ── Print user reviews ──
        System.out.println("\n  💬 User Reviews:");
        if (reviews.isEmpty()) {
            System.out.println("     No reviews yet.");
        } else {
            for (Review r : reviews) {
                System.out.printf(
                    "     @%-12s | ⭐ %d/10 | %s%n",
                    r.getUsername(),
                    r.getRating(),
                    r.getComment()
                );
            }
        }

        System.out.println("  ─────────────────────────────────────────────");
    }

    /**
     * Standard toString — useful for debugging.
     */
    @Override
    public String toString() {
        return String.format("Movie[%s - %s (%s) | %s | ⭐%.1f]",
                movieId, title, language, genre, rating);
    }
}