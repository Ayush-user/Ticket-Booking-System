package Ticket_Booking_System.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import Ticket_Booking_System.dto.MovieRequestDTO;
import Ticket_Booking_System.dto.MovieResponseDTO;
import Ticket_Booking_System.entity.Movie;
import Ticket_Booking_System.enums.MovieStatus;
import Ticket_Booking_System.exception.ResourceNotFoundException;
import Ticket_Booking_System.repository.MovieRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovieService {
		
	private final MovieRepository movieRepository;
	
	private final ModelMapper modelMapper;
	
	public MovieResponseDTO createMovie(MovieRequestDTO movieRequestDTO) {
		Movie movie=modelMapper.map(movieRequestDTO, Movie.class);
		movie.setStatus(MovieStatus.ACTIVE);
		Movie saveMovie=movieRepository.save(movie);
		return modelMapper.map(saveMovie, MovieResponseDTO.class);
		
	}
	
	public List<MovieResponseDTO> getAllMovies() {
		List<Movie> movies=movieRepository.findAll();
//		List<MovieResponseDTO> movieResponseDTOs=new ArrayList<>();
//		for(Movie movie:movies) {
//			movieResponseDTOs.add(modelMapper.map(movie, MovieResponseDTO.class));
//		}
		
		return movies.stream().map(movie-> modelMapper.map(movie, MovieResponseDTO.class)).toList();
//		return movieResponseDTOs;
	}
	
	public MovieResponseDTO getMovieById(long id) {
		Movie movie= movieRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Movie id "+id+" is not found"));
		//Normally we use optional class and from that we get the data ..but here we have used orElseThrow ...
		
		return modelMapper.map(movie, MovieResponseDTO.class);
	}
	
	
	public List<MovieResponseDTO> getAllMoviesByTitle(String title) {
		List<Movie> movies=movieRepository.findByTitleIgnoreCase(title);
		
		return movies.stream().map(movie-> modelMapper.map(movie, MovieResponseDTO.class)).toList();
	}
	
	public List<MovieResponseDTO> getAllMoviesByGenre(String genre) {
		List<Movie> movies=movieRepository.findByGenreIgnoreCase(genre);
		
		return movies.stream().map(movie->modelMapper.map(movie, MovieResponseDTO.class)).toList();
	}
	
	public MovieResponseDTO updateMovieById(long id,MovieRequestDTO movieRequestDTO) {
		Movie movie= movieRepository.findById(id).orElseThrow(
				()->new ResourceNotFoundException("Movie id "+id+" is not found"));
		
//		movie.setDescription(movieRequestDTO.getDescription());
//		movie.setCertificate(movieRequestDTO.getCertificate());
//		movie.setDuration(movieRequestDTO.getDuration());
//		movie.setGenre(movieRequestDTO.getGenre());
//		movie.setLanguage(movieRequestDTO.getLanguage());
//		movie.setReleaseDate(movieRequestDTO.getReleaseDate());
//		movie.setTitle(movieRequestDTO.getTitle());
		
		modelMapper.map(movieRequestDTO, movie);
		
		Movie updatedMovie=movieRepository.save(movie);
		return modelMapper.map(updatedMovie,MovieResponseDTO.class);
	}
	
	
//	public void deleteMovieById(long id) {
//		movieRepository.deleteById(id);
//	}
}
