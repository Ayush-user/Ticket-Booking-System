package Ticket_Booking_System.dto;

import java.time.LocalDate;

import Ticket_Booking_System.enums.MovieStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieResponseDTO {

	private Long id;
	private String title;
	private String description;
	private String genre;
	private String language;
	private Integer duration;
	private LocalDate releaseDate;
	private String certificate;
	private MovieStatus status;
}

