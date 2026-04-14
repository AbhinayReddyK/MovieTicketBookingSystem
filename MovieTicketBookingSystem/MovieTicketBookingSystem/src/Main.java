import model.*;
import service.*;
import utils.*;

import java.util.Scanner;

/**
 * MAIN ENTRY POINT
 * This is where the program starts.
 * It shows the welcome menu and routes the user to login, register, or admin panel.
 */
public class Main {

    // Shared Scanner used throughout the app (passed to services)
    static Scanner scanner = new Scanner(System.in);

    // Services — each handles one area of the app
    static UserService    userService    = new UserService();
    static MovieService   movieService   = new MovieService();
    static TheaterService theaterService = new TheaterService();
    static BookingService bookingService = new BookingService(movieService, theaterService);
    static TicketService  ticketService  = new TicketService();
    static AdminService   adminService   = new AdminService(movieService, theaterService, bookingService);

    public static void main(String[] args) {

        ConsoleHelper.printBanner();   // Print welcome banner

        boolean running = true;

        while (running) {
            ConsoleHelper.printMainMenu();
            int choice = InputHelper.getIntInput(scanner, "Enter choice: ");

            switch (choice) {
                case 1 -> handleRegister();
                case 2 -> handleLogin();
                case 3 -> handleAdminLogin();
                case 4 -> {
                    System.out.println("\n👋 Thank you for using BookMyShow Lite. Goodbye!");
                    running = false;
                }
                default -> System.out.println("❌ Invalid choice. Please enter 1-4.");
            }
        }

        scanner.close();
    }

    // ─────────────────────────────────────────────
    //  REGISTER
    // ─────────────────────────────────────────────
    private static void handleRegister() {
        System.out.println("\n╔══════════════════════════╗");
        System.out.println("║      USER REGISTRATION    ║");
        System.out.println("╚══════════════════════════╝");

        System.out.print("Enter name     : ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter username : ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter password : ");
        String password = scanner.nextLine().trim();

        System.out.print("Enter email    : ");
        String email = scanner.nextLine().trim();

        // Delegate to UserService — returns success/failure message
        String result = userService.register(name, username, password, email);
        System.out.println("\n" + result);
    }

    // ─────────────────────────────────────────────
    //  USER LOGIN
    // ─────────────────────────────────────────────
    private static void handleLogin() {
        System.out.println("\n╔══════════════════════════╗");
        System.out.println("║        USER LOGIN         ║");
        System.out.println("╚══════════════════════════╝");

        System.out.print("Username : ");
        String username = scanner.nextLine().trim();

        System.out.print("Password : ");
        String password = scanner.nextLine().trim();

        // Try to log in — returns null if credentials are wrong
        User loggedInUser = userService.login(username, password);

        if (loggedInUser == null) {
            System.out.println("❌ Invalid username or password. Please try again.");
        } else {
            System.out.println("✅ Welcome back, " + loggedInUser.getName() + "!");
            showUserMenu(loggedInUser);   // Go to user dashboard
        }
    }

    // ─────────────────────────────────────────────
    //  ADMIN LOGIN
    // ─────────────────────────────────────────────
    private static void handleAdminLogin() {
        System.out.println("\n╔══════════════════════════╗");
        System.out.println("║       ADMIN LOGIN         ║");
        System.out.println("╚══════════════════════════╝");

        System.out.print("Admin Username : ");
        String username = scanner.nextLine().trim();

        System.out.print("Admin Password : ");
        String password = scanner.nextLine().trim();

        // Hardcoded admin credentials for simplicity (no DB needed)
        if (username.equals("admin") && password.equals("admin123")) {
            System.out.println("✅ Admin access granted.");
            showAdminMenu();
        } else {
            System.out.println("❌ Invalid admin credentials.");
        }
    }

    // ─────────────────────────────────────────────
    //  USER DASHBOARD MENU
    // ─────────────────────────────────────────────
    private static void showUserMenu(User user) {
        boolean loggedIn = true;

        while (loggedIn) {
            ConsoleHelper.printUserMenu(user.getName());
            int choice = InputHelper.getIntInput(scanner, "Enter choice: ");

            switch (choice) {
                case 1  -> movieService.displayAllMovies();
                case 2  -> movieService.searchMovies(scanner);
                case 3  -> movieService.viewMovieDetails(scanner);
                case 4  -> bookingService.bookTicket(scanner, user, ticketService);
                case 5  -> bookingService.viewBookingHistory(user);
                case 6  -> bookingService.cancelBooking(scanner, user);
                case 7  -> {
                    System.out.println("👋 Logged out successfully.");
                    loggedIn = false;
                }
                default -> System.out.println("❌ Invalid choice. Enter 1-7.");
            }
        }
    }

    // ─────────────────────────────────────────────
    //  ADMIN DASHBOARD MENU
    // ─────────────────────────────────────────────
    private static void showAdminMenu() {
        boolean adminLoggedIn = true;

        while (adminLoggedIn) {
            ConsoleHelper.printAdminMenu();
            int choice = InputHelper.getIntInput(scanner, "Enter choice: ");

            switch (choice) {
                case 1  -> adminService.addMovie(scanner);
                case 2  -> adminService.updateShowTimings(scanner);
                case 3  -> adminService.viewAllBookings();
                case 4  -> movieService.displayAllMovies();
                case 5  -> {
                    System.out.println("👋 Admin logged out.");
                    adminLoggedIn = false;
                }
                default -> System.out.println("❌ Invalid choice. Enter 1-5.");
            }
        }
    }
}