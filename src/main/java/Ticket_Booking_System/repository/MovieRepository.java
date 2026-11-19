package Ticket_Booking_System.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import Ticket_Booking_System.entity.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

	List<Movie> findByTitleIgnoreCase(String title);
	List<Movie> findByGenreIgnoreCase(String genre);
	
}
