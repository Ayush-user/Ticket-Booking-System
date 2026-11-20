package Ticket_Booking_System.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ticket_Booking_System.dto.BookingRequestDTO;
import Ticket_Booking_System.dto.BookingResponseDTO;
import Ticket_Booking_System.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
      
    @PostMapping
    public ResponseEntity<BookingResponseDTO> createBooking(@Valid @RequestBody BookingRequestDTO requestDTO,Authentication authentication) {

        String customerEmail = authentication.getName();

        BookingResponseDTO response = bookingService.createBooking(customerEmail, requestDTO);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

//    @PostMapping("/users/{userId}/shows/{showId}/seats/{showSeatId}")
//    public ResponseEntity<BookingResponseDTO> createBooking(
//            @PathVariable Long userId,
//            @PathVariable Long showId,
//            @PathVariable Long showSeatId) {
//
//        BookingResponseDTO responseDTO = bookingService.createBooking(
//                userId, showId, showSeatId);
//
//        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
//    }
}