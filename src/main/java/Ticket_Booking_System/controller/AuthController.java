package Ticket_Booking_System.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Ticket_Booking_System.dto.AuthResponseDTO;
import Ticket_Booking_System.dto.LoginRequestDTO;
import Ticket_Booking_System.dto.RegisterRequestDTO;
import Ticket_Booking_System.dto.UserResponseDTO;
import Ticket_Booking_System.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody RegisterRequestDTO requestDTO) {

    	return new ResponseEntity<>(authService.register(requestDTO), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO requestDTO) {

    	return new ResponseEntity<>(authService.login(requestDTO), HttpStatus.OK);
    }
}