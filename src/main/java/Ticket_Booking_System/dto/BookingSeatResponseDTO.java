package Ticket_Booking_System.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingSeatResponseDTO {

    private Long id;
    
    private Long bookingId;

    private Long showSeatId;

    private BigDecimal priceAtBooking;
}