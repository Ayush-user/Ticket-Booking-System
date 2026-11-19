package Ticket_Booking_System.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TheatreRequestDTO {
	
	@NotBlank
	private String name;
	@NotBlank
    private String address;
	@NotBlank
    private String location;
}
