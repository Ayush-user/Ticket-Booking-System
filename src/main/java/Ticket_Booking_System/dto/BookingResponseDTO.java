package Ticket_Booking_System.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import Ticket_Booking_System.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponseDTO {

	 	private Long id;

	    private Long customerId;

	    private String customerName;

	    private Long showId;

	    private BigDecimal totalAmount;

	    private LocalDateTime bookingDateTime;

	    private BookingStatus status;

	    private String bookingReference;
	    
	    private List<BookingSeatResponseDTO> seats;
}