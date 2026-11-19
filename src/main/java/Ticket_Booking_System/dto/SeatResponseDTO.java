package Ticket_Booking_System.dto;

import Ticket_Booking_System.enums.SeatType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatResponseDTO {

    private Long id;
    private String seatNumber;
    private SeatType seatType;
    private Long screenId;
    private String screenName;
}