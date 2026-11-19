package Ticket_Booking_System.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import Ticket_Booking_System.dto.SeatRequestDTO;
import Ticket_Booking_System.dto.SeatResponseDTO;
import Ticket_Booking_System.entity.Screen;
import Ticket_Booking_System.entity.Seat;
import Ticket_Booking_System.exception.ResourceNotFoundException;
import Ticket_Booking_System.repository.ScreenRepository;
import Ticket_Booking_System.repository.SeatRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {
	
	 	private final SeatRepository seatRepository;
	    private final ScreenRepository screenRepository;
	    private final ModelMapper modelMapper;
	    
	    public SeatResponseDTO addSeat(Long screenId, 
	    		SeatRequestDTO requestDTO) {

	        Screen screen = screenRepository.findById(screenId)
	                .orElseThrow(() -> new ResourceNotFoundException(
	                        "Screen not found with ID: " + screenId));

	        boolean exists = seatRepository.existsByScreenIdAndSeatNumber(
	                screenId, requestDTO.getSeatNumber());

	        if (exists) {
	            throw new IllegalArgumentException(
	                    "Seat number already exists in this screen");
	        }

	        Seat seat = modelMapper.map(requestDTO, Seat.class);
	        seat.setScreen(screen);

	        Seat savedSeat = seatRepository.save(seat);

	        SeatResponseDTO responseDTO =
	                modelMapper.map(savedSeat, SeatResponseDTO.class);

	        responseDTO.setScreenId(screen.getId());
	        responseDTO.setScreenName(screen.getName());

	        return responseDTO;
	    }

	    public List<SeatResponseDTO> getSeatsByScreenId(Long screenId) {

	        screenRepository.findById(screenId)
	                .orElseThrow(() -> new ResourceNotFoundException(
	                        "Screen not found with ID: " + screenId));

	        List<Seat> seats = seatRepository.findByScreenId(screenId);
	        List<SeatResponseDTO> seatResponseDTOs = new ArrayList<>();
	        for(Seat seat:seats) {
	        	SeatResponseDTO seatResponseDTO = 
	        			modelMapper.map(seat, SeatResponseDTO.class);
	        	seatResponseDTO.setScreenId(seat.getScreen().getId());
	        	seatResponseDTO.setScreenName(seat.getScreen().getName());
	        	seatResponseDTOs.add(seatResponseDTO);
	        }
	        return seatResponseDTOs;
	    }
}
