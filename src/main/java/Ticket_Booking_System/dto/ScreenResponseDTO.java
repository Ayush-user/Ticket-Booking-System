package Ticket_Booking_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScreenResponseDTO {

    private Long id;
    private String name;
    private Integer totalSeats;
    private Long theatreId;
    private String theatreName;
}