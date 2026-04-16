package service;

import model.Movie;
import model.Show;
import utils.ConsoleHelper;
import utils.InputHelper;

import java.util.List;
import java.util.Scanner;

/**
 * SERVICE: AdminService
 * Handles all admin-only operations:
 * - Add new movies
 * - Update show timings
 * - View all bookings
 *
 * OOP Concept: Dependency Injection + Single Responsibility
 * Admin operations are isolated here and delegate to other services.
 */
public class AdminService {

    // ─────────────────────────────────────────────
    //  DEPENDENCIES
    // ─────────────────────────────────────────────

    private MovieService   movieService;
    private TheaterService theaterService;
    private BookingService bookingService;


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    public AdminService(MovieService movieService,
                        TheaterService theaterService,
                        BookingService bookingService) {
        this.movieService   = movieService;
        this.theaterService = theaterService;
        this.bookingService = bookingService;
    }


    // ─────────────────────────────────────────────
    //  ADD MOVIE
    // ─────────────────────────────────────────────

    /**
     * Delegates movie creation to MovieService.
     * Then optionally adds a show to the new movie.
     */
    public void addMovie(Scanner scanner) {
        // MovieService handles all input and validation
        movieService.addMovie(scanner);

        // Ask if admin wants to add a show right away
        boolean addShow = InputHelper.getYesNo(scanner,
                "\n  Do you want to add a show for this movie now? (y/n): ");

        if (addShow) {
            addShowToExistingMovie(scanner);
        }
    }


    // ─────────────────────────────────────────────
    //  UPDATE SHOW TIMINGS
    // ─────────────────────────────────────────────

    /**
     * Lets admin update the time and date of an existing show.
     * Searches through all movies to find the target show.
     */
    public void updateShowTimings(Scanner scanner) {
        ConsoleHelper.printSectionHeader("🕐  UPDATE SHOW TIMINGS");

        // Show all movies so admin can pick one
        movieService.displayAllMovies();
        System.out.print("\n  Enter Movie ID: ");
        String movieId = scanner.nextLine().trim().toUpperCase();

        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            System.out.println("  ❌ Movie not found.");
            return;
        }

        // List all shows for this movie
        List<Show> shows = movie.getShows();
        if (shows.isEmpty()) {
            System.out.println("  ❌ No shows found for this movie.");
            return;
        }

        System.out.println("\n  Shows for: " + movie.getTitle());
        ConsoleHelper.printDivider();
        for (int i = 0; i < shows.size(); i++) {
            shows.get(i).printSummary(i + 1);
        }

        int showChoice = InputHelper.getIntInRange(scanner,
                "\n  Select Show to update (1-" + shows.size() + "): ", 1, shows.size());
        Show selectedShow = shows.get(showChoice - 1);

        // Get new time and date
        System.out.print("  New Show Time (e.g. 07:30 PM): ");
        String newTime = scanner.nextLine().trim();

        System.out.print("  New Show Date (e.g. 2025-06-10): ");
        String newDate = scanner.nextLine().trim();

        // Apply updates
        selectedShow.setShowTime(newTime);
        selectedShow.setShowDate(newDate);

        System.out.println("  ✅ Show " + selectedShow.getShowId()
                + " updated to " + newDate + " at " + newTime);
    }


    // ─────────────────────────────────────────────
    //  VIEW ALL BOOKINGS
    // ─────────────────────────────────────────────

    /**
     * Delegates to BookingService to print all bookings system-wide.
     */
    public void viewAllBookings() {
        bookingService.viewAllBookings();
    }


    // ─────────────────────────────────────────────
    //  ADD SHOW TO EXISTING MOVIE
    // ─────────────────────────────────────────────

    /**
     * Adds a new show to an existing movie.
     * Admin provides movie ID, theater, time, and date.
     */
    private void addShowToExistingMovie(Scanner scanner) {
        ConsoleHelper.printSectionHeader("📅  ADD SHOW TO MOVIE");

        movieService.displayAllMovies();
        System.out.print("  Enter Movie ID: ");
        String movieId = scanner.nextLine().trim().toUpperCase();

        if (movieService.getMovieById(movieId) == null) {
            System.out.println("  ❌ Movie not found.");
            return;
        }

        // Show available theaters
        theaterService.displayAllTheaters();
        System.out.print("  Enter Theater ID: ");
        String theaterId = scanner.nextLine().trim().toUpperCase();

        var theater = theaterService.getTheaterById(theaterId);
        if (theater == null) {
            System.out.println("  ❌ Theater not found.");
            return;
        }

        System.out.print("  Show Time (e.g. 06:00 PM): ");
        String showTime = scanner.nextLine().trim();

        System.out.print("  Show Date (e.g. 2025-06-15): ");
        String showDate = scanner.nextLine().trim();

        boolean success = movieService.addShowToMovie(
                movieId, theaterId, theater.getTheaterName(), showTime, showDate);

        if (success) {
            System.out.println("  ✅ Show added successfully!");
        } else {
            System.out.println("  ❌ Failed to add show.");
        }
    }
}