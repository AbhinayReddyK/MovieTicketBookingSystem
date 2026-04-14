package service;

import model.Booking;
import model.Ticket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SERVICE: TicketService
 * Responsible for generating and printing tickets after a booking is confirmed.
 *
 * OOP Concept: Single Responsibility
 * This class only handles ticket creation — nothing else.
 */
public class TicketService {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Creates a Ticket object from a confirmed Booking and prints it.
     *
     * @param ticketId  Unique ticket ID e.g. "TK001"
     * @param booking   The confirmed Booking to generate ticket from
     */
    public void generateTicket(String ticketId, Booking booking) {
        String issuedAt = LocalDateTime.now().format(FORMATTER);

        // Build ticket from booking data
        Ticket ticket = new Ticket(
                ticketId,
                booking.getBookingId(),
                booking.getUsername(),
                booking.getMovieTitle(),
                booking.getTheaterName(),
                booking.getShowDate(),
                booking.getShowTime(),
                booking.getSeatClass(),
                booking.getSeatIds(),
                booking.getTotalAmount(),
                issuedAt
        );

        // Print the formatted ticket to console
        ticket.printTicket();
    }
}