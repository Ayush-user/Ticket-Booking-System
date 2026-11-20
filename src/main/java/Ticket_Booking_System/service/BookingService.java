package Ticket_Booking_System.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Ticket_Booking_System.dto.BookingRequestDTO;
import Ticket_Booking_System.dto.BookingResponseDTO;
import Ticket_Booking_System.dto.BookingSeatResponseDTO;
import Ticket_Booking_System.entity.Booking;
import Ticket_Booking_System.entity.BookingSeat;
import Ticket_Booking_System.entity.Show;
import Ticket_Booking_System.entity.ShowSeat;
import Ticket_Booking_System.entity.User;
import Ticket_Booking_System.enums.BookingStatus;
import Ticket_Booking_System.enums.SeatStatus;
import Ticket_Booking_System.enums.ShowStatus;
import Ticket_Booking_System.exception.ResourceNotFoundException;
import Ticket_Booking_System.repository.BookingRepository;
import Ticket_Booking_System.repository.BookingSeatRepository;
import Ticket_Booking_System.repository.ShowRepository;
import Ticket_Booking_System.repository.ShowSeatRepository;
import Ticket_Booking_System.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ShowRepository showRepository;
    private final ShowSeatRepository showSeatRepository;
    private final BookingSeatRepository bookingSeatRepository;

    @Transactional
    public BookingResponseDTO createBooking(
            String customerEmail,
            BookingRequestDTO requestDTO) {

        // 1. Find the logged-in customer
        User customer = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer not found"));

        // 2. Find the selected show
        Show show = showRepository.findById(requestDTO.getShowId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Show not found with ID: "
                                + requestDTO.getShowId()));

        // 3. Check show status
        if (show.getStatus() != ShowStatus.SCHEDULED) {
            throw new IllegalArgumentException(
                    "This show is not available for booking");
        }

        // 4. Get selected seat IDs
        List<Long> seatIds = requestDTO.getShowSeatIds();

        // 5. Prevent duplicate seat IDs in one request
        Set<Long> uniqueSeatIds = new HashSet<>(seatIds);

        if (uniqueSeatIds.size() != seatIds.size()) {
            throw new IllegalArgumentException(
                    "Duplicate seat IDs are not allowed");
        }

        // 6. Lock selected show seats in the database
        List<ShowSeat> selectedSeats =
                showSeatRepository.findAllByIdForUpdate(seatIds);

        // 7. Verify that all requested seats exist
        if (selectedSeats.size() != seatIds.size()) {
            throw new ResourceNotFoundException(
                    "One or more selected seats do not exist");
        }

        // 8. Validate availability and calculate total amount
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (ShowSeat showSeat : selectedSeats) {

            // Ensure seat belongs to the selected show
            if (!showSeat.getShow().getId()
                    .equals(show.getId())) {

                throw new IllegalArgumentException(
                        "Selected seat does not belong to this show");
            }

            // Ensure seat is available
            if (showSeat.getStatus() != SeatStatus.AVAILABLE) {

                throw new IllegalArgumentException(
                        "One or more selected seats are unavailable");
            }

            // Calculate total amount
            totalAmount = totalAmount.add(showSeat.getPrice());
        }

        // 9. Create Booking
        Booking booking = new Booking();

        booking.setCustomer(customer);
        booking.setShow(show);
        booking.setTotalAmount(totalAmount);
        booking.setBookingDateTime(LocalDateTime.now());
        booking.setStatus(BookingStatus.CONFIRMED);

        // Generate unique booking reference
        booking.setBookingReference(
                "BK-" + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase());

        Booking savedBooking = bookingRepository.save(booking);

        // 10. Create BookingSeat records
        List<BookingSeat> bookingSeats = new ArrayList<>();

        for (ShowSeat showSeat : selectedSeats) {

            BookingSeat bookingSeat = new BookingSeat();

            bookingSeat.setBooking(savedBooking);
            bookingSeat.setShowSeat(showSeat);
            bookingSeat.setPriceAtBooking(showSeat.getPrice());

            bookingSeats.add(bookingSeat);

            // Mark seat as booked
            showSeat.setStatus(SeatStatus.BOOKED);
        }

        // 11. Save all booking seats
        List<BookingSeat> savedBookingSeats =
                bookingSeatRepository.saveAll(bookingSeats);

        // 12. Update all selected show seats
        showSeatRepository.saveAll(selectedSeats);

        // 13. Prepare response DTO
        BookingResponseDTO response = new BookingResponseDTO();

        response.setId(savedBooking.getId());
        response.setCustomerId(customer.getId());
        response.setCustomerName(customer.getName());
        response.setShowId(show.getId());
        response.setTotalAmount(totalAmount);
        response.setBookingDateTime(
                savedBooking.getBookingDateTime());
        response.setStatus(savedBooking.getStatus());
        response.setBookingReference(
                savedBooking.getBookingReference());

        // Include booked seat details
        List<BookingSeatResponseDTO> seatResponses =
                new ArrayList<>();

        for (BookingSeat bookingSeat : savedBookingSeats) {

            BookingSeatResponseDTO seatResponse =
                    new BookingSeatResponseDTO();

            seatResponse.setId(bookingSeat.getId());
            seatResponse.setBookingId(savedBooking.getId());
            seatResponse.setShowSeatId(
                    bookingSeat.getShowSeat().getId());
            seatResponse.setPriceAtBooking(
                    bookingSeat.getPriceAtBooking());

            seatResponses.add(seatResponse);
        }

        response.setSeats(seatResponses);

        return response;
    }
}
