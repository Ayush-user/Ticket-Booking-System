package Ticket_Booking_System.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import Ticket_Booking_System.dto.ShowRequestDTO;
import Ticket_Booking_System.dto.ShowResponseDTO;
import Ticket_Booking_System.entity.Movie;
import Ticket_Booking_System.entity.Screen;
import Ticket_Booking_System.entity.Seat;
import Ticket_Booking_System.entity.Show;
import Ticket_Booking_System.entity.ShowSeat;
import Ticket_Booking_System.enums.MovieStatus;
import Ticket_Booking_System.enums.SeatStatus;
import Ticket_Booking_System.enums.SeatType;
import Ticket_Booking_System.enums.ShowStatus;
import Ticket_Booking_System.exception.ResourceNotFoundException;
import Ticket_Booking_System.repository.MovieRepository;
import Ticket_Booking_System.repository.ScreenRepository;
import Ticket_Booking_System.repository.SeatRepository;
import Ticket_Booking_System.repository.ShowRepository;
import Ticket_Booking_System.repository.ShowSeatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShowService {

    private final ShowRepository showRepository;
    private final MovieRepository movieRepository;
    private final ScreenRepository screenRepository;
    private final SeatRepository seatRepository;
    private final ShowSeatRepository showSeatRepository;
    
    private final ModelMapper modelMapper;

    @Transactional
    public ShowResponseDTO addShow(Long movieId, Long screenId, ShowRequestDTO requestDTO) {

        if (!requestDTO.getEndTime().isAfter(requestDTO.getStartTime())) {
            throw new IllegalArgumentException(
                    "End time must be after start time");
        }

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Movie not found with ID: " + movieId));

        if (movie.getStatus() != MovieStatus.ACTIVE) {
            throw new IllegalArgumentException(
                    "Cannot schedule a show for an inactive movie");
        }

        Screen screen = screenRepository.findById(screenId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Screen not found with ID: " + screenId));

        boolean overlapping = showRepository.existsOverlappingShow(
                screenId,
                requestDTO.getShowDate(),
                requestDTO.getStartTime(),
                requestDTO.getEndTime());

        if (overlapping) {
            throw new IllegalArgumentException(
                    "Another show is already scheduled during this time on the screen");
        }

        Show show = modelMapper.map(requestDTO, Show.class);
        
        show.setMovie(movie);
        show.setScreen(screen);
        show.setStatus(ShowStatus.SCHEDULED);
        
        Show savedShow = showRepository.save(show);
        
        // Get all physical seats belonging to this screen
        List<Seat> seats = seatRepository.findByScreenId(screenId);
        
        // Create ShowSeat records for this show
        List<ShowSeat> showSeats = new ArrayList<>();
        
        for(Seat seat : seats) {
        	
        	ShowSeat showSeat = new ShowSeat();

            showSeat.setShow(savedShow);
            showSeat.setSeat(seat);
            showSeat.setStatus(SeatStatus.AVAILABLE);
            showSeat.setPrice(
            		seat.getSeatType().equals(SeatType.PREMIUM)? 
            				savedShow.getTicketPrice().add(BigDecimal.valueOf(100)) : savedShow.getTicketPrice());

            showSeats.add(showSeat);
        }
        
        // Save all ShowSeat records
        showSeatRepository.saveAll(showSeats);
        
        ShowResponseDTO responseDTO = modelMapper.map(savedShow, ShowResponseDTO.class);
       
        responseDTO.setTheatreId(screen.getTheatre().getId());
        responseDTO.setTheatreName(screen.getTheatre().getName());
        
        return responseDTO;
    }

    public List<ShowResponseDTO> getAllShows(
            Long movieId, Long theatreId, LocalDate showDate) {

        List<Show> shows = showRepository.findShowsByFilters(
                movieId, theatreId, showDate);
        List<ShowResponseDTO> responseDTOs = new ArrayList<>();
        for(Show show : shows) {
        	responseDTOs.add(modelMapper.map(show, ShowResponseDTO.class));
        }

        return responseDTOs;
    }

    public ShowResponseDTO getShowById(Long id) {

        Show show = showRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Show not found with ID: " + id));

        return modelMapper.map(show, ShowResponseDTO.class);
    }
}