package Ticket_Booking_System.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ticket_Booking_System.entity.ShowSeat;
import Ticket_Booking_System.service.ShowSeatService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/shows")
@RequiredArgsConstructor
public class ShowSeatController {

    private final ShowSeatService showSeatService;

    @GetMapping("/{showId}/seats")
    public ResponseEntity<List<ShowSeat>> getShowSeats(
            @PathVariable Long showId) {

        return new ResponseEntity<>(
        		showSeatService.getShowSeats(showId), HttpStatus.OK);
    }
}