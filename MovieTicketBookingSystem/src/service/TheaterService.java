package service;

import model.Theater;

import java.util.ArrayList;
import java.util.List;

/**
 * SERVICE: TheaterService
 * Manages all theaters in the system.
 * Pre-loads 3 sample theaters with auto-generated seats on startup.
 *
 * OOP Concept: Encapsulation + Single Responsibility
 */
public class TheaterService {

    // ─────────────────────────────────────────────
    //  IN-MEMORY THEATER STORE
    // ─────────────────────────────────────────────

    private List<Theater> theaters = new ArrayList<>();


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR — Pre-load Sample Theaters
    // ─────────────────────────────────────────────

    public TheaterService() {
        // 3 theaters pre-loaded — seats auto-generated inside Theater constructor
        theaters.add(new Theater("T001", "PVR Cinemas",    "Hyderabad"));
        theaters.add(new Theater("T002", "INOX Multiplex", "Hyderabad"));
        theaters.add(new Theater("T003", "Cinepolis Gold", "Hyderabad"));
    }


    // ─────────────────────────────────────────────
    //  LOOKUP
    // ─────────────────────────────────────────────

    /**
     * Finds a Theater by its ID.
     * Returns null if not found.
     *
     * @param theaterId  e.g. "T001"
     */
    public Theater getTheaterById(String theaterId) {
        for (Theater theater : theaters) {
            if (theater.getTheaterId().equalsIgnoreCase(theaterId)) {
                return theater;
            }
        }
        return null;
    }

    /**
     * Returns all theaters.
     * Used for display and selection during booking.
     */
    public List<Theater> getAllTheaters() { return theaters; }

    /**
     * Prints a list of all theaters — used during booking flow.
     */
    public void displayAllTheaters() {
        System.out.println("\n  Available Theaters:");
        for (Theater t : theaters) {
            System.out.printf("  [%-4s]  %-18s | %s%n",
                    t.getTheaterId(), t.getTheaterName(), t.getLocation());
        }
    }
}