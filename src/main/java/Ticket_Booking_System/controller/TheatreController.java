package Ticket_Booking_System.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ticket_Booking_System.dto.TheatreRequestDTO;
import Ticket_Booking_System.dto.TheatreResponseDTO;
import Ticket_Booking_System.service.TheatreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/theatres")
@RequiredArgsConstructor
public class TheatreController {

	private final TheatreService theatreService ;
	
	@PostMapping
	public ResponseEntity<TheatreResponseDTO> addtheatre(@Valid @RequestBody TheatreRequestDTO requestDTO) {
		return new ResponseEntity<>(theatreService.addTheatre(requestDTO),HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<TheatreResponseDTO>> getAllTheatre() {
		return new ResponseEntity<>(theatreService.getAllTheatre(),HttpStatus.OK);
	}
}
