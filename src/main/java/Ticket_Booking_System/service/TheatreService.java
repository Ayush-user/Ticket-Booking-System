package Ticket_Booking_System.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import Ticket_Booking_System.dto.TheatreRequestDTO;
import Ticket_Booking_System.dto.TheatreResponseDTO;
import Ticket_Booking_System.entity.Theatre;
import Ticket_Booking_System.repository.TheatreRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TheatreService {
		
	private final TheatreRepository theatreRepository;
	private final ModelMapper modelMapper;
	
	public TheatreResponseDTO addTheatre(TheatreRequestDTO requestDTO) {
		Theatre theatre=modelMapper.map(requestDTO, Theatre.class);
		
		Theatre savedTheatre=theatreRepository.save(theatre);
		
		return modelMapper.map(savedTheatre, TheatreResponseDTO.class);
		
	}
	
	public List<TheatreResponseDTO> getAllTheatre() {
		List<Theatre> theatres=theatreRepository.findAll();
		List<TheatreResponseDTO> responseDTOs=new ArrayList<>();
		
		for(Theatre theatre:theatres) {
			responseDTOs.add(modelMapper.map(theatre, TheatreResponseDTO.class));
		}
		
		return responseDTOs;
	}
}
