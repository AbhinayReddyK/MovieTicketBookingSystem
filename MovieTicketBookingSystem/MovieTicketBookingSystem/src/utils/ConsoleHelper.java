package utils;

/**
 * UTILITY: ConsoleHelper
 * Handles all menu printing and banner display.
 * Keeps all UI strings in one place — easy to update.
 *
 * OOP Concept: Utility class — all static methods, no instantiation needed.
 */
public class ConsoleHelper {

    // ─────────────────────────────────────────────
    //  WELCOME BANNER
    // ─────────────────────────────────────────────

    public static void printBanner() {
        System.out.println("\n");
        System.out.println("  ╔══════════════════════════════════════════════════════╗");
        System.out.println("  ║                                                      ║");
        System.out.println("  ║        🎬   BOOKMYSHOW LITE   🎬                     ║");
        System.out.println("  ║        Online Movie Ticket Booking System            ║");
        System.out.println("  ║                                                      ║");
        System.out.println("  ╚══════════════════════════════════════════════════════╝");
        System.out.println();
    }


    // ─────────────────────────────────────────────
    //  MAIN MENU
    // ─────────────────────────────────────────────

    public static void printMainMenu() {
        System.out.println("\n╔══════════════════════════╗");
        System.out.println("║        MAIN MENU          ║");
        System.out.println("╠══════════════════════════╣");
        System.out.println("║  1. Register              ║");
        System.out.println("║  2. Login                 ║");
        System.out.println("║  3. Admin Panel           ║");
        System.out.println("║  4. Exit                  ║");
        System.out.println("╚══════════════════════════╝");
    }


    // ─────────────────────────────────────────────
    //  USER DASHBOARD MENU
    // ─────────────────────────────────────────────

    public static void printUserMenu(String userName) {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.printf( "║  👤 Welcome, %-16s║%n", userName);
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  1. View All Movies           ║");
        System.out.println("║  2. Search Movies             ║");
        System.out.println("║  3. View Movie Details        ║");
        System.out.println("║  4. Book Tickets              ║");
        System.out.println("║  5. My Booking History        ║");
        System.out.println("║  6. Cancel Booking            ║");
        System.out.println("║  7. Logout                    ║");
        System.out.println("╚══════════════════════════════╝");
    }


    // ─────────────────────────────────────────────
    //  ADMIN MENU
    // ─────────────────────────────────────────────

    public static void printAdminMenu() {
        System.out.println("\n╔══════════════════════════════╗");
        System.out.println("║       🔧 ADMIN PANEL          ║");
        System.out.println("╠══════════════════════════════╣");
        System.out.println("║  1. Add New Movie             ║");
        System.out.println("║  2. Update Show Timings       ║");
        System.out.println("║  3. View All Bookings         ║");
        System.out.println("║  4. View All Movies           ║");
        System.out.println("║  5. Logout                    ║");
        System.out.println("╚══════════════════════════════╝");
    }


    // ─────────────────────────────────────────────
    //  SECTION DIVIDER
    // ─────────────────────────────────────────────

    /** Prints a simple divider line — keeps output clean between sections. */
    public static void printDivider() {
        System.out.println("  ─────────────────────────────────────────────────");
    }

    /** Prints a section header with a title. */
    public static void printSectionHeader(String title) {
        System.out.println("\n  ┌─────────────────────────────────────────────────┐");
        System.out.printf( "  │  %-48s│%n", title);
        System.out.println("  └─────────────────────────────────────────────────┘");
    }
}