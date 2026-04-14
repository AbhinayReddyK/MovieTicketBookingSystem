package service;

import model.User;
import utils.ValidationHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * SERVICE: UserService
 * Handles user registration and login.
 * Stores all users in memory using a HashMap (username → User).
 *
 * OOP Concept: Encapsulation + Single Responsibility
 * This class only deals with user management — nothing else.
 */
public class UserService {

    // ─────────────────────────────────────────────
    //  IN-MEMORY USER STORE
    // ─────────────────────────────────────────────

    // Key = username (unique), Value = User object
    private Map<String, User> users = new HashMap<>();


    // ─────────────────────────────────────────────
    //  REGISTER
    // ─────────────────────────────────────────────

    /**
     * Registers a new user after validating all fields.
     * Returns a message string indicating success or the specific error.
     *
     * @param name      Full name
     * @param username  Unique login ID
     * @param password  Must be 6+ chars
     * @param email     Must be valid format
     * @return          Success or error message
     */
    public String register(String name, String username,
                           String password, String email) {

        // ── Validate each field ──
        if (!ValidationHelper.isValidName(name)) {
            return "❌ Invalid name. Use 2–30 letters only.";
        }
        if (!ValidationHelper.isValidUsername(username)) {
            return "❌ Invalid username. Use 4–15 alphanumeric characters, no spaces.";
        }
        if (!ValidationHelper.isValidPassword(password)) {
            return "❌ Invalid password. Must be at least 6 characters with no spaces.";
        }
        if (!ValidationHelper.isValidEmail(email)) {
            return "❌ Invalid email format. Example: user@example.com";
        }

        // ── Check for duplicate username ──
        if (users.containsKey(username.toLowerCase())) {
            return "❌ Username '" + username + "' is already taken. Please choose another.";
        }

        // ── All good — create and store user ──
        User newUser = new User(name, username.toLowerCase(), password, email);
        users.put(username.toLowerCase(), newUser);

        return "✅ Registration successful! Welcome, " + name + ". You can now log in.";
    }


    // ─────────────────────────────────────────────
    //  LOGIN
    // ─────────────────────────────────────────────

    /**
     * Validates username and password.
     * Returns the User object on success, null on failure.
     *
     * @param username  Login username
     * @param password  Login password
     * @return          Matched User or null
     */
    public User login(String username, String password) {
        // Look up user by username (case-insensitive)
        User user = users.get(username.toLowerCase());

        // Check if user exists AND password matches
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }

        return null;   // Login failed
    }


    // ─────────────────────────────────────────────
    //  UTILITY
    // ─────────────────────────────────────────────

    /**
     * Checks if a username is already registered.
     * @param username  Username to check
     * @return          true if taken
     */
    public boolean userExists(String username) {
        return users.containsKey(username.toLowerCase());
    }

    /**
     * Returns total number of registered users.
     * Used by AdminService for stats display.
     */
    public int getTotalUsers() {
        return users.size();
    }
}