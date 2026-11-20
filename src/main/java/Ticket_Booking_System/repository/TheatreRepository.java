package Ticket_Booking_System.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Ticket_Booking_System.entity.Theatre;
import Ticket_Booking_System.entity.User;

public interface TheatreRepository extends JpaRepository<Theatre,Long>{

	
}
