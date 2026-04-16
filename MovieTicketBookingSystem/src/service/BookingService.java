package service;

import model.*;
import utils.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * SERVICE: BookingService
 * Handles the entire ticket booking flow:
 * - Movie and show selection
 * - Seat class and seat selection
 * - Booking confirmation
 * - Ticket generation
 * - Booking history
 * - Booking cancellation
 *
 * OOP Concept: Encapsulation + Dependency Injection
 * (Depends on MovieService and TheaterService passed via constructor)
 */
public class BookingService {

    // ─────────────────────────────────────────────
    //  DEPENDENCIES + STORAGE
    // ─────────────────────────────────────────────

    private MovieService   movieService;    // To fetch movies and shows
    private TheaterService theaterService;  // To fetch theaters and seats

    // All bookings stored in memory — key = bookingId
    private Map<String, Booking> allBookings = new HashMap<>();
    private int bookingCounter = 1;   // For IDs: BK001, BK002...
    private int ticketCounter  = 1;   // For IDs: TK001, TK002...

    // Formatter for booking timestamp
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    // ─────────────────────────────────────────────
    //  CONSTRUCTOR
    // ─────────────────────────────────────────────

    public BookingService(MovieService movieService, TheaterService theaterService) {
        this.movieService   = movieService;
        this.theaterService = theaterService;
    }


    // ─────────────────────────────────────────────
    //  BOOK TICKET  (main flow)
    // ─────────────────────────────────────────────

    /**
     * Full booking flow:
     * Step 1 → Select movie
     * Step 2 → Select show
     * Step 3 → Select seat class
     * Step 4 → View seat layout
     * Step 5 → Select seats
     * Step 6 → Confirm and generate ticket
     */
    public void bookTicket(Scanner scanner, User user, TicketService ticketService) {
        ConsoleHelper.printSectionHeader("🎟️  BOOK TICKETS");

        // ── STEP 1: Select Movie ──
        movieService.displayAllMovies();
        System.out.print("\n  Enter Movie ID to book: ");
        String movieId = scanner.nextLine().trim().toUpperCase();

        Movie movie = movieService.getMovieById(movieId);
        if (movie == null) {
            System.out.println("  ❌ Movie not found.");
            return;
        }

        // ── STEP 2: Select Show ──
        List<Show> shows = movie.getShows();
        if (shows.isEmpty()) {
            System.out.println("  ❌ No shows available for this movie.");
            return;
        }

        System.out.println("\n  Available Shows for: " + movie.getTitle());
        ConsoleHelper.printDivider();
        for (int i = 0; i < shows.size(); i++) {
            shows.get(i).printSummary(i + 1);
        }

        int showChoice = InputHelper.getIntInRange(scanner,
                "\n  Select Show (1-" + shows.size() + "): ", 1, shows.size());
        Show selectedShow = shows.get(showChoice - 1);

        if (!selectedShow.isActive()) {
            System.out.println("  ❌ This show is currently inactive.");
            return;
        }

        // ── STEP 3: Select Seat Class ──
        System.out.println("\n  Select Seat Class:");
        System.out.println("  1. BALCONY  — ₹" + Theater.BALCONY_PRICE);
        System.out.println("  2. GOLD     — ₹" + Theater.GOLD_PRICE);
        System.out.println("  3. SILVER   — ₹" + Theater.SILVER_PRICE);

        int classChoice = InputHelper.getIntInRange(scanner, "  Enter choice: ", 1, 3);
        String seatClass = switch (classChoice) {
            case 1 -> "BALCONY";
            case 2 -> "GOLD";
            default -> "SILVER";
        };

        // ── STEP 4: Get Theater and Show Seat Layout ──
        Theater theater = theaterService.getTheaterById(selectedShow.getTheaterId());
        if (theater == null) {
            System.out.println("  ❌ Theater not found.");
            return;
        }

        SeatLayoutPrinter.printFullLayout(theater);

        // ── STEP 5: How many seats? ──
        int numSeats;
        while (true) {
            numSeats = InputHelper.getIntInput(scanner,
                    "\n  How many seats do you want to book? (1-10): ");
            if (ValidationHelper.isValidSeatCount(numSeats)) break;
            System.out.println("  ⚠️  Please enter a number between 1 and 10.");
        }

        // ── STEP 6: Select each seat ──
        System.out.println("\n  Enter Seat IDs one by one.");
        System.out.println("  Format: CLASS-BLOCK-ROW-COL   e.g. GOLD-2-B-3");
        System.out.println("  Your selected class: " + seatClass);

        List<String> selectedSeatIds = new ArrayList<>();
        int pricePerSeat = theater.getPriceForClass(seatClass);

        for (int i = 1; i <= numSeats; i++) {
            while (true) {
                System.out.print("  Seat " + i + ": ");
                String seatInput = scanner.nextLine().trim().toUpperCase();

                // ── Validate class matches ──
                if (!seatInput.startsWith(seatClass)) {
                    System.out.println("  ⚠️  Please select a " + seatClass + " seat only.");
                    continue;
                }

                Seat seat = theater.getSeatById(seatInput);

                if (seat == null) {
                    System.out.println("  ❌ Seat '" + seatInput + "' not found. Try again.");
                    continue;
                }

                if (seat.isBooked()) {
                    System.out.println("  ❌ Seat '" + seatInput + "' is already booked. Choose another.");
                    continue;
                }

                if (selectedSeatIds.contains(seatInput)) {
                    System.out.println("  ⚠️  You already selected this seat. Choose a different one.");
                    continue;
                }

                selectedSeatIds.add(seatInput);
                System.out.println("  ✅ Seat " + seatInput + " selected.");
                break;
            }
        }

        // ── STEP 7: Show summary and confirm ──
        int totalAmount = pricePerSeat * numSeats;

        System.out.println("\n  ┌─── BOOKING SUMMARY ─────────────────────────┐");
        System.out.printf( "  │  Movie     : %-32s│%n", movie.getTitle());
        System.out.printf( "  │  Show      : %s  %s%-16s│%n",
                selectedShow.getShowDate(), selectedShow.getShowTime(), "");
        System.out.printf( "  │  Theater   : %-32s│%n", theater.getTheaterName());
        System.out.printf( "  │  Class     : %-32s│%n", seatClass);
        System.out.printf( "  │  Seats     : %-32s│%n", String.join(", ", selectedSeatIds));
        System.out.printf( "  │  Amount    : ₹%-31d│%n", totalAmount);
        System.out.println("  └──────────────────────────────────────────────┘");

        boolean confirm = InputHelper.getYesNo(scanner, "\n  Confirm booking? (y/n): ");
        if (!confirm) {
            System.out.println("  ❌ Booking cancelled by user.");
            return;
        }

        // ── STEP 8: Mark seats as booked ──
        for (String sid : selectedSeatIds) {
            Seat seat = theater.getSeatById(sid);
            if (seat != null) seat.setBooked(true);
        }

        // ── STEP 9: Create Booking object ──
        String bookingId  = String.format("BK%03d", bookingCounter++);
        String bookingTime = LocalDateTime.now().format(FORMATTER);

        Booking booking = new Booking(
                bookingId,
                user.getUsername(),
                movie.getTitle(),
                selectedShow.getShowId(),
                selectedShow.getShowTime(),
                selectedShow.getShowDate(),
                theater.getTheaterName(),
                selectedSeatIds,
                seatClass,
                totalAmount,
                bookingTime
        );

        allBookings.put(bookingId, booking);
        user.addBookingId(bookingId);

        // ── STEP 10: Generate and print ticket ──
        String ticketId = String.format("TK%03d", ticketCounter++);
        ticketService.generateTicket(ticketId, booking);

        System.out.println("  🎉 Booking Successful! Booking ID: " + bookingId);
    }


    // ─────────────────────────────────────────────
    //  VIEW BOOKING HISTORY
    // ─────────────────────────────────────────────

    /**
     * Displays all bookings made by the logged-in user.
     * Shows both confirmed and cancelled bookings.
     */
    public void viewBookingHistory(User user) {
        ConsoleHelper.printSectionHeader("📜  MY BOOKING HISTORY");

        List<String> ids = user.getBookingIds();

        if (ids.isEmpty()) {
            System.out.println("  ⚠️  You have no bookings yet.");
            return;
        }

        System.out.printf("  %-8s  %-22s  %-12s  %-10s  %-6s  %-8s  %s%n",
                "ID", "Movie", "Date", "Time", "Seats", "Amount", "Status");
        ConsoleHelper.printDivider();

        for (String id : ids) {
            Booking b = allBookings.get(id);
            if (b != null) b.printSummary();
        }
    }


    // ─────────────────────────────────────────────
    //  CANCEL BOOKING
    // ─────────────────────────────────────────────

    /**
     * Cancels a booking:
     * - Verifies the booking belongs to this user
     * - Frees up the seats in the theater
     * - Marks booking as CANCELLED
     * - Removes booking ID from user's list
     */
    public void cancelBooking(Scanner scanner, User user) {
        ConsoleHelper.printSectionHeader("❌  CANCEL BOOKING");

        if (user.getBookingIds().isEmpty()) {
            System.out.println("  ⚠️  You have no bookings to cancel.");
            return;
        }

        // Show existing bookings first
        viewBookingHistory(user);

        System.out.print("\n  Enter Booking ID to cancel: ");
        String bookingId = scanner.nextLine().trim().toUpperCase();

        // ── Validate ownership ──
        if (!user.hasBooking(bookingId)) {
            System.out.println("  ❌ Booking ID '" + bookingId + "' not found in your history.");
            return;
        }

        Booking booking = allBookings.get(bookingId);
        if (booking == null) {
            System.out.println("  ❌ Booking record not found.");
            return;
        }

        // ── Already cancelled? ──
        if (booking.getBookingStatus().equals("CANCELLED")) {
            System.out.println("  ⚠️  This booking is already cancelled.");
            return;
        }

        // ── Confirm cancellation ──
        booking.printDetails();
        boolean confirm = InputHelper.getYesNo(scanner,
                "\n  Are you sure you want to cancel this booking? (y/n): ");
        if (!confirm) {
            System.out.println("  ℹ️  Cancellation aborted.");
            return;
        }

        // ── Free up the seats ──
        Theater theater = theaterService.getTheaterById(
                getTheaterIdFromShowId(booking.getShowId()));

        if (theater != null) {
            for (String seatId : booking.getSeatIds()) {
                Seat seat = theater.getSeatById(seatId);
                if (seat != null) seat.setBooked(false);   // Make available again
            }
        }

        // ── Update booking status ──
        booking.setBookingStatus("CANCELLED");

        System.out.println("  ✅ Booking " + bookingId + " has been successfully cancelled.");
        System.out.println("  💡 Seats are now available for others to book.");
    }


    // ─────────────────────────────────────────────
    //  ADMIN: VIEW ALL BOOKINGS
    // ─────────────────────────────────────────────

    /**
     * Prints all bookings in the system — used by AdminService.
     */
    public void viewAllBookings() {
        ConsoleHelper.printSectionHeader("📋  ALL BOOKINGS (ADMIN VIEW)");

        if (allBookings.isEmpty()) {
            System.out.println("  ⚠️  No bookings have been made yet.");
            return;
        }

        System.out.printf("  %-8s  %-12s  %-22s  %-10s  %-6s  %-8s  %s%n",
                "BookingID", "User", "Movie", "Date", "Seats", "Amount", "Status");
        ConsoleHelper.printDivider();

        for (Booking b : allBookings.values()) {
            System.out.printf("  %-8s  %-12s  %-22s  %-10s  %-6d  ₹%-7d  %s%n",
                    b.getBookingId(),
                    b.getUsername(),
                    b.getMovieTitle(),
                    b.getShowDate(),
                    b.getSeatIds().size(),
                    b.getTotalAmount(),
                    b.getBookingStatus().equals("CONFIRMED") ? "✅" : "❌"
            );
        }

        System.out.println("\n  Total bookings: " + allBookings.size());
    }


    // ─────────────────────────────────────────────
    //  HELPER: Get Theater ID from Show
    // ─────────────────────────────────────────────

    /**
     * Looks up which theater a show belongs to.
     * Searches through all movies and their shows.
     *
     * @param showId  The show ID e.g. "SH003"
     * @return        The theater ID or empty string if not found
     */
    private String getTheaterIdFromShowId(String showId) {
        for (Movie movie : movieService.getAllMovies()) {
            for (Show show : movie.getShows()) {
                if (show.getShowId().equalsIgnoreCase(showId)) {
                    return show.getTheaterId();
                }
            }
        }
        return "";
    }
}