package Ticket_Booking_System.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import Ticket_Booking_System.dto.AuthResponseDTO;
import Ticket_Booking_System.dto.LoginRequestDTO;
import Ticket_Booking_System.dto.RegisterRequestDTO;
import Ticket_Booking_System.dto.UserResponseDTO;
import Ticket_Booking_System.entity.User;
import Ticket_Booking_System.repository.UserRepository;
import Ticket_Booking_System.security.JwtService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;

    public UserResponseDTO register(RegisterRequestDTO requestDTO) {

        if (userRepository.existsByEmail(requestDTO.getEmail())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = modelMapper.map(requestDTO, User.class);

        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        user.setRole("CUSTOMER");

        User savedUser = userRepository.save(user);

        return modelMapper.map(savedUser, UserResponseDTO.class);
    }

    public AuthResponseDTO login(LoginRequestDTO requestDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword()));

        String token = jwtService.generateToken(requestDTO.getEmail());

        return new AuthResponseDTO(token, "Bearer");
    }
}