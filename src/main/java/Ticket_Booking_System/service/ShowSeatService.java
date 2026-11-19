package Ticket_Booking_System.service;

import java.util.List;

import org.springframework.stereotype.Service;

import Ticket_Booking_System.entity.ShowSeat;
import Ticket_Booking_System.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowSeatService {

    private final ShowSeatRepository showSeatRepository;

    public List<ShowSeat> getShowSeats(Long showId) {
        return showSeatRepository.findByShowId(showId);
    }
}