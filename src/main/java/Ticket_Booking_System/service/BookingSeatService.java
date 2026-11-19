package Ticket_Booking_System.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import Ticket_Booking_System.dto.BookingSeatResponseDTO;
import Ticket_Booking_System.entity.BookingSeat;
import Ticket_Booking_System.exception.ResourceNotFoundException;
import Ticket_Booking_System.repository.BookingRepository;
import Ticket_Booking_System.repository.BookingSeatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingSeatService {

    private final BookingSeatRepository bookingSeatRepository;
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    public List<BookingSeatResponseDTO> getBookingSeats(Long bookingId) {

        if (!bookingRepository.existsById(bookingId)) {
            throw new ResourceNotFoundException(
                    "Booking not found with ID: " + bookingId);
        }

        List<BookingSeat> bookingSeats = bookingSeatRepository.findByBookingId(bookingId);

        List<BookingSeatResponseDTO> responseDTOs = new ArrayList<>();

        for (BookingSeat bookingSeat : bookingSeats) {

            BookingSeatResponseDTO dto = modelMapper.map(
                    bookingSeat, BookingSeatResponseDTO.class);

            dto.setBookingId(bookingSeat.getBooking().getId());
            dto.setShowSeatId(bookingSeat.getShowSeat().getId());

            responseDTOs.add(dto);
        }

        return responseDTOs;
    }
}