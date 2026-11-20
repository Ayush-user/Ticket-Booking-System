package Ticket_Booking_System.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowRequestDTO {

	@NotNull
	@FutureOrPresent
	private LocalDate showDate;

	@NotNull
	private LocalTime startTime;

	@NotNull
	private LocalTime endTime;

	@NotNull
	private BigDecimal ticketPrice;
}