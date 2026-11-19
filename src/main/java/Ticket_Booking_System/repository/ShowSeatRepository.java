package Ticket_Booking_System.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Ticket_Booking_System.entity.ShowSeat;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, Long> {

	List<ShowSeat> findByShowId(Long showId);
}
