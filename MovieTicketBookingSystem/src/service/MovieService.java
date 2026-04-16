package service;

import model.Movie;
import model.Review;
import model.Show;
import utils.ConsoleHelper;
import utils.InputHelper;
import utils.ValidationHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * SERVICE: MovieService
 * Manages all movie-related operations:
 * - View all movies
 * - Search by name / language / genre
 * - View detailed movie info
 * - Add movies (used by AdminService)
 * - Add shows to movies
 *
 * Pre-loads sample movies on startup so the app has data to work with.
 *
 * OOP Concept: Encapsulation + Single Responsibility
 */
public class MovieService {

    // ─────────────────────────────────────────────
    //  IN-MEMORY MOVIE STORE
    // ─────────────────────────────────────────────

    private List<Movie> movies = new ArrayList<>();
    private int         movieCounter = 1;   // For generating IDs: M001, M002...
    private int         showCounter  = 1;   // For generating show IDs: SH001...


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR — Pre-load Sample Data
    // ─────────────────────────────────────────────

    public MovieService() {
        loadSampleMovies();   // Fill with demo data on startup
    }

    /**
     * Loads sample movies and shows so the app works out of the box.
     * In a real app this would come from a database.
     */
    private void loadSampleMovies() {

        // ── Movie 1 ──
        Movie m1 = new Movie("M001", "Inception", "English", "Sci-Fi",
                148, 9.0, "A thief enters dreams to plant an idea in a target's mind.");
        m1.addShow(new Show("SH001", "M001", "T001", "PVR Cinemas",   "10:00 AM", "2025-06-01"));
        m1.addShow(new Show("SH002", "M001", "T002", "INOX Multiplex", "07:00 PM", "2025-06-01"));
        m1.addReview(new Review("john99",   9, "Mind-blowing concept. Must watch!"));
        m1.addReview(new Review("sara_k",   8, "Visually stunning and gripping."));
        movies.add(m1);

        // ── Movie 2 ──
        Movie m2 = new Movie("M002", "KGF Chapter 2", "Kannada", "Action",
                168, 8.5, "Rocky continues his rise to power in the gold mines of KGF.");
        m2.addShow(new Show("SH003", "M002", "T001", "PVR Cinemas",    "01:00 PM", "2025-06-01"));
        m2.addShow(new Show("SH004", "M002", "T003", "Cinepolis Gold", "09:30 PM", "2025-06-01"));
        m2.addReview(new Review("rocky_fan", 10, "Rocky Bhai forever! Epic climax."));
        m2.addReview(new Review("moviebuff", 8,  "Great action. Sanjay Dutt was terrifying."));
        movies.add(m2);

        // ── Movie 3 ──
        Movie m3 = new Movie("M003", "3 Idiots", "Hindi", "Comedy",
                170, 9.2, "Three engineering students challenge the education system.");
        m3.addShow(new Show("SH005", "M003", "T002", "INOX Multiplex", "11:00 AM", "2025-06-02"));
        m3.addShow(new Show("SH006", "M003", "T003", "Cinepolis Gold", "05:00 PM", "2025-06-02"));
        m3.addReview(new Review("aamir_fan", 10, "Aal Izz Well! Timeless classic."));
        movies.add(m3);

        // ── Movie 4 ──
        Movie m4 = new Movie("M004", "RRR", "Telugu", "Action",
                182, 8.8, "Two legendary Indian revolutionaries fight against British rule.");
        m4.addShow(new Show("SH007", "M004", "T001", "PVR Cinemas",    "02:30 PM", "2025-06-02"));
        m4.addShow(new Show("SH008", "M004", "T002", "INOX Multiplex", "08:00 PM", "2025-06-02"));
        m4.addReview(new Review("rajamouli_fan", 9, "Naatu Naatu! Goosebumps!"));
        m4.addReview(new Review("telugu_pride",  9, "Best Indian movie in years."));
        movies.add(m4);

        // ── Movie 5 ──
        Movie m5 = new Movie("M005", "The Dark Knight", "English", "Action",
                152, 9.5, "Batman faces the Joker, a criminal mastermind who wants to create chaos.");
        m5.addShow(new Show("SH009", "M005", "T003", "Cinepolis Gold", "04:00 PM", "2025-06-03"));
        m5.addShow(new Show("SH010", "M005", "T001", "PVR Cinemas",    "09:00 PM", "2025-06-03"));
        m5.addReview(new Review("batman_fan", 10, "Heath Ledger as Joker is iconic."));
        movies.add(m5);

        // Update counters so new additions don't clash
        movieCounter = 6;
        showCounter  = 11;
    }


    // ─────────────────────────────────────────────
    //  DISPLAY ALL MOVIES
    // ─────────────────────────────────────────────

    /**
     * Prints a formatted table of all movies.
     * Shows ID, title, language, genre, duration, and rating.
     */
    public void displayAllMovies() {
        ConsoleHelper.printSectionHeader("🎬  ALL AVAILABLE MOVIES");

        if (movies.isEmpty()) {
            System.out.println("  ⚠️  No movies available at the moment.");
            return;
        }

        // Table header
        System.out.printf("  %-6s  %-22s  %-8s  %-10s  %-7s  %s%n",
                "ID", "Title", "Language", "Genre", "Duration", "Rating");
        ConsoleHelper.printDivider();

        // One row per movie
        for (Movie movie : movies) {
            movie.printSummary();
        }

        ConsoleHelper.printDivider();
        System.out.println("  Total: " + movies.size() + " movie(s) listed.");
    }


    // ─────────────────────────────────────────────
    //  SEARCH MOVIES
    // ─────────────────────────────────────────────

    /**
     * Lets the user search by movie name, language, or genre.
     * Shows a search sub-menu and filters the movie list accordingly.
     */
    public void searchMovies(Scanner scanner) {
        ConsoleHelper.printSectionHeader("🔍  SEARCH MOVIES");

        System.out.println("  Search by:");
        System.out.println("  1. Movie Name");
        System.out.println("  2. Language");
        System.out.println("  3. Genre");

        int choice = InputHelper.getIntInRange(scanner, "  Enter choice: ", 1, 3);
        System.out.print("  Enter search term: ");
        String term = scanner.nextLine().trim().toLowerCase();

        List<Movie> results = new ArrayList<>();

        for (Movie movie : movies) {
            switch (choice) {
                case 1 -> { if (movie.getTitle().toLowerCase().contains(term))    results.add(movie); }
                case 2 -> { if (movie.getLanguage().toLowerCase().contains(term)) results.add(movie); }
                case 3 -> { if (movie.getGenre().toLowerCase().contains(term))    results.add(movie); }
            }
        }

        // Display results
        if (results.isEmpty()) {
            System.out.println("\n  ⚠️  No movies found matching '" + term + "'.");
        } else {
            System.out.println("\n  Found " + results.size() + " result(s):\n");
            System.out.printf("  %-6s  %-22s  %-8s  %-10s  %-7s  %s%n",
                    "ID", "Title", "Language", "Genre", "Duration", "Rating");
            ConsoleHelper.printDivider();
            for (Movie m : results) m.printSummary();
        }
    }


    // ─────────────────────────────────────────────
    //  VIEW MOVIE DETAILS
    // ─────────────────────────────────────────────

    /**
     * Asks for a Movie ID and prints full details including shows and reviews.
     */
    public void viewMovieDetails(Scanner scanner) {
        ConsoleHelper.printSectionHeader("📋  MOVIE DETAILS");
        displayAllMovies();

        System.out.print("\n  Enter Movie ID (e.g. M001): ");
        String id = scanner.nextLine().trim().toUpperCase();

        Movie movie = getMovieById(id);
        if (movie == null) {
            System.out.println("  ❌ Movie ID '" + id + "' not found.");
        } else {
            movie.printDetails();
        }
    }


    // ─────────────────────────────────────────────
    //  ADD MOVIE  (used by AdminService)
    // ─────────────────────────────────────────────

    /**
     * Adds a new movie via admin input.
     * Validates each field before creating the Movie object.
     */
    public void addMovie(Scanner scanner) {
        ConsoleHelper.printSectionHeader("➕  ADD NEW MOVIE");

        String title    = InputHelper.getNonEmptyString(scanner, "  Movie Title    : ");
        String language = InputHelper.getNonEmptyString(scanner, "  Language       : ");
        String genre    = InputHelper.getNonEmptyString(scanner, "  Genre          : ");
        String desc     = InputHelper.getNonEmptyString(scanner, "  Description    : ");

        // Duration with validation
        int duration;
        while (true) {
            duration = InputHelper.getIntInput(scanner, "  Duration (min) : ");
            if (ValidationHelper.isValidDuration(duration)) break;
            System.out.println("  ⚠️  Duration must be between 30 and 300 minutes.");
        }

        // Rating with validation
        double rating;
        while (true) {
            rating = InputHelper.getDoubleInput(scanner, "  Rating (0-10)  : ");
            if (ValidationHelper.isValidRating(rating)) break;
            System.out.println("  ⚠️  Rating must be between 0.0 and 10.0.");
        }

        // Generate ID and create movie
        String movieId = String.format("M%03d", movieCounter++);
        Movie newMovie = new Movie(movieId, title, language, genre, duration, rating, desc);
        movies.add(newMovie);

        System.out.println("  ✅ Movie '" + title + "' added successfully with ID: " + movieId);
    }


    // ─────────────────────────────────────────────
    //  ADD SHOW TO MOVIE  (used by AdminService)
    // ─────────────────────────────────────────────

    /**
     * Adds a new show (screening) to an existing movie.
     *
     * @param movieId     The movie to add a show to
     * @param theaterId   The theater ID
     * @param theaterName The theater name
     * @param showTime    Time string e.g. "07:00 PM"
     * @param showDate    Date string e.g. "2025-06-05"
     * @return            true if added successfully
     */
    public boolean addShowToMovie(String movieId, String theaterId,
                                  String theaterName, String showTime, String showDate) {
        Movie movie = getMovieById(movieId);
        if (movie == null) return false;

        String showId = String.format("SH%03d", showCounter++);
        Show show = new Show(showId, movieId, theaterId, theaterName, showTime, showDate);
        movie.addShow(show);
        return true;
    }


    // ─────────────────────────────────────────────
    //  LOOKUP HELPERS
    // ─────────────────────────────────────────────

    /**
     * Finds a movie by its ID. Returns null if not found.
     * @param movieId  e.g. "M001"
     */
    public Movie getMovieById(String movieId) {
        for (Movie movie : movies) {
            if (movie.getMovieId().equalsIgnoreCase(movieId)) {
                return movie;
            }
        }
        return null;
    }

    /**
     * Returns the full list of movies.
     * Used by AdminService and BookingService.
     */
    public List<Movie> getAllMovies() { return movies; }
}