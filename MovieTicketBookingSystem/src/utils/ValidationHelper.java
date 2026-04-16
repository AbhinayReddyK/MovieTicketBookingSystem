package utils;

/**
 * UTILITY: ValidationHelper
 * Contains all input validation logic used across the system.
 * Keeps validation rules in one place — easy to update.
 *
 * OOP Concept: Utility class — all static methods.
 */
public class ValidationHelper {

    // ─────────────────────────────────────────────
    //  USERNAME VALIDATION
    // ─────────────────────────────────────────────

    /**
     * Username rules:
     * - Must be 4–15 characters long
     * - Only letters and numbers allowed (no spaces or symbols)
     *
     * @param username  The username to validate
     * @return          true if valid
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) return false;
        // Regex: alphanumeric, 4 to 15 chars
        return username.matches("^[a-zA-Z0-9]{4,15}$");
    }


    // ─────────────────────────────────────────────
    //  PASSWORD VALIDATION
    // ─────────────────────────────────────────────

    /**
     * Password rules:
     * - Must be at least 6 characters long
     * - No spaces allowed
     *
     * @param password  The password to validate
     * @return          true if valid
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) return false;
        return password.length() >= 6 && !password.contains(" ");
    }


    // ─────────────────────────────────────────────
    //  EMAIL VALIDATION
    // ─────────────────────────────────────────────

    /**
     * Basic email format check.
     * Must contain "@" and "." after the "@" symbol.
     *
     * @param email  The email to validate
     * @return       true if format looks valid
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        // Simple check: has @ and a dot after it
        int atIndex = email.indexOf("@");
        if (atIndex < 1) return false;
        String domain = email.substring(atIndex + 1);
        return domain.contains(".");
    }


    // ─────────────────────────────────────────────
    //  NAME VALIDATION
    // ─────────────────────────────────────────────

    /**
     * Name rules:
     * - Must be 2–30 characters
     * - Only letters and spaces allowed
     *
     * @param name  The name to validate
     * @return      true if valid
     */
    public static boolean isValidName(String name) {
        if (name == null || name.isEmpty()) return false;
        return name.matches("^[a-zA-Z ]{2,30}$");
    }


    // ─────────────────────────────────────────────
    //  MOVIE RATING VALIDATION
    // ─────────────────────────────────────────────

    /**
     * Rating must be between 0.0 and 10.0
     *
     * @param rating  The rating to validate
     * @return        true if within valid range
     */
    public static boolean isValidRating(double rating) {
        return rating >= 0.0 && rating <= 10.0;
    }


    // ─────────────────────────────────────────────
    //  MOVIE DURATION VALIDATION
    // ─────────────────────────────────────────────

    /**
     * Duration must be between 30 and 300 minutes.
     *
     * @param duration  Duration in minutes
     * @return          true if within valid range
     */
    public static boolean isValidDuration(int duration) {
        return duration >= 30 && duration <= 300;
    }


    // ─────────────────────────────────────────────
    //  SEAT COUNT VALIDATION
    // ─────────────────────────────────────────────

    /**
     * User can book between 1 and 10 seats per booking.
     *
     * @param count  Number of seats requested
     * @return       true if valid
     */
    public static boolean isValidSeatCount(int count) {
        return count >= 1 && count <= 10;
    }


    // ─────────────────────────────────────────────
    //  REVIEW RATING VALIDATION
    // ─────────────────────────────────────────────

    /**
     * Review rating must be an integer from 1 to 10.
     *
     * @param rating  The review rating
     * @return        true if between 1 and 10
     */
    public static boolean isValidReviewRating(int rating) {
        return rating >= 1 && rating <= 10;
    }
}