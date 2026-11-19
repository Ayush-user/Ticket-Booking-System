package Ticket_Booking_System.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import Ticket_Booking_System.dto.ScreenRequestDTO;
import Ticket_Booking_System.dto.ScreenResponseDTO;
import Ticket_Booking_System.entity.Screen;
import Ticket_Booking_System.entity.Theatre;
import Ticket_Booking_System.exception.ResourceNotFoundException;
import Ticket_Booking_System.repository.ScreenRepository;
import Ticket_Booking_System.repository.TheatreRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ScreenService {
	
	private final ScreenRepository screenRepository;
	private final TheatreRepository theatreRepository;
	private final ModelMapper modelMapper;
	
	
	public ScreenResponseDTO addScreen(long theatreId, ScreenRequestDTO requestDTO) {
		
		Theatre theatre=theatreRepository.findById(theatreId).orElseThrow(
				()->new ResourceNotFoundException("Theatre Not Found"));
		
		Screen screen=modelMapper.map(requestDTO, Screen.class);
		screen.setTheatre(theatre);
		
		Screen savedScreen=screenRepository.save(screen);
		return modelMapper.map(savedScreen, ScreenResponseDTO.class);
	}
}
