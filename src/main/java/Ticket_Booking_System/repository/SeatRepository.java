package Ticket_Booking_System.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Ticket_Booking_System.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	
	 boolean existsByScreenIdAndSeatNumber(
	            Long screenId,
	            String seatNumber
	    );

	    List<Seat> findByScreenId(Long screenId);
}
