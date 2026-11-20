package Ticket_Booking_System.dto;


import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    @NotNull(message = "Show ID is required")
    @Positive(message = "Show ID must be positive")
    private Long showId;

    @NotEmpty(message = "Please select at least one seat")
    private List<
        @NotNull(message = "Seat ID cannot be null")
        @Positive(message = "Seat ID must be positive")
        Long
    > showSeatIds;
}
