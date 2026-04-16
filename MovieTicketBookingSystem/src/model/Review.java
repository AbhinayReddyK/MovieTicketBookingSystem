package model;

/**
 * MODEL: Review
 * Represents a user review for a movie.
 * Stores the username, a star rating (1–10), and a text comment.
 *
 * OOP Concept: Encapsulation
 * Simple value object — created once, not modified.
 */
public class Review {

    // ─────────────────────────────────────────────
    //  FIELDS
    // ─────────────────────────────────────────────

    private String username;   // Who wrote the review
    private int    rating;     // Star rating 1–10
    private String comment;    // Text comment


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    public Review(String username, int rating, String comment) {
        this.username = username;
        this.rating   = rating;
        this.comment  = comment;
    }


    // ─────────────────────────────────────────────
    //  GETTERS  (no setters — reviews are immutable)
    // ─────────────────────────────────────────────

    public String getUsername() { return username; }
    public int    getRating()   { return rating; }
    public String getComment()  { return comment; }


    // ─────────────────────────────────────────────
    //  UTILITY
    // ─────────────────────────────────────────────

    @Override
    public String toString() {
        return String.format("Review[@%s | ⭐%d/10 | %s]", username, rating, comment);
    }
}