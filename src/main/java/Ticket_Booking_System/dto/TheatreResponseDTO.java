package Ticket_Booking_System.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TheatreResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String location;
}