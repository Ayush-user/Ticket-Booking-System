package Ticket_Booking_System.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Ticket_Booking_System.entity.BookingSeat;

public interface BookingSeatRepository extends JpaRepository<BookingSeat, Long> {

	List<BookingSeat> findByBookingId(Long bookingId);
}
