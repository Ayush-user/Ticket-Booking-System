package Ticket_Booking_System.entity;

import java.time.LocalDate;

import Ticket_Booking_System.enums.MovieStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="movies")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Movie {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	private String title;
	private String description;
	private String genre;
	private String language;
	private Integer duration;
	private LocalDate releaseDate;
	private String certificate;
	
	@Enumerated(EnumType.STRING)
	private MovieStatus status;
}
