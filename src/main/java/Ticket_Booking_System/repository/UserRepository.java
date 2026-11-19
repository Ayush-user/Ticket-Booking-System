package Ticket_Booking_System.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import Ticket_Booking_System.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);
}
