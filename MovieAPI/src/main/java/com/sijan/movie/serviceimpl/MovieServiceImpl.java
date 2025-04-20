package com.sijan.movie.serviceimpl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sijan.movie.model.Actor;
import com.sijan.movie.model.Movie;
import com.sijan.movie.repository.MovieRepository;
import com.sijan.movie.service.ActorService;
import com.sijan.movie.service.MovieService;

@Service
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	private MovieRepository movieRepo;
	
	@Autowired
	private ActorService actorService;

	@Override
    public Movie saveMovie(Movie movie) {

        if (!StringUtils.hasText(movie.getTitle())) {
            throw new IllegalArgumentException("Movie title cannot be empty.");
        }
        if (movie.getReleaseDate() == null) {
            throw new IllegalArgumentException("Release date is required.");
        }

        if (movie.getReleaseDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Release date cannot be in the future.");
        }

        if (movieRepo.existsByTitleIgnoreCaseAndReleaseDate(movie.getTitle(), movie.getReleaseDate())) {
            throw new RuntimeException("A movie with this title and release date already exists.");
        }

        String title = movie.getTitle().trim();
        movie.setTitle(Character.toUpperCase(title.charAt(0)) + title.substring(1));

        return movieRepo.save(movie);
    }

    @Override
    public Movie updateMovie(Long id, Movie updatedMovie) {
    	if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Movie ID.");
        }
        Movie existingMovie = getMovieById(id);

        if (StringUtils.hasText(updatedMovie.getTitle())) {
            existingMovie.setTitle(updatedMovie.getTitle().trim());
        }

        if (StringUtils.hasText(updatedMovie.getGenre())) {
            existingMovie.setGenre(updatedMovie.getGenre().trim());
        }

        if (updatedMovie.getReleaseDate() != null) {
            if (updatedMovie.getReleaseDate().isAfter(LocalDate.now())) {
                throw new IllegalArgumentException("Release date cannot be in the future.");
            }
            existingMovie.setReleaseDate(updatedMovie.getReleaseDate());
        }

        Double newPrice = updatedMovie.getRentalPrice();
        if (newPrice != null) {
            if (newPrice < 0) {
                throw new IllegalArgumentException("Rental price must be a positive value.");
            }
            existingMovie.setRentalPrice(newPrice);
        }

        if (updatedMovie.getActors() != null && !updatedMovie.getActors().isEmpty()) {
            existingMovie.setActors(updatedMovie.getActors());
        }

        return movieRepo.save(existingMovie);
    }

    @Override
    public void deleteMovie(Long id) {
    	if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Movie ID.");
        }
        if (!movieRepo.existsById(id)) {
            throw new RuntimeException("Movie with ID " + id + " does not exist.");
        }
        movieRepo.deleteById(id);
    }

    @Override
    public Movie getMovieById(Long id) {
        return movieRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie with ID " + id + " not found."));
    }

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieRepo.findAll();
        if (movies.isEmpty()) {
            throw new RuntimeException("No movies found.");
        }
        return movies;
    }

    @Override
    public Movie addActorsToMovie(Long movieId, List<Long> actorIds) {
        Movie movie = getMovieById(movieId);

        Set<Long> existingActorIds = movie.getActors().stream()
                .map(Actor::getId)
                .collect(Collectors.toSet());

        List<Actor> newActors = actorIds.stream()
                .filter(id -> {
                    if (existingActorIds.contains(id)) {
                        System.out.println("Actor with ID " + id + " is already in the movie. Skipping.");
                        return false;
                    }
                    return true;
                })
                .map(actorService::getActorById)
                .collect(Collectors.toList());

        movie.getActors().addAll(newActors);
        return movieRepo.save(movie);
    }

    @Override
    public List<Movie> getMoviesByActorId(Long actorId) {
    	if (actorId == null || actorId < 0) {
            throw new IllegalArgumentException("Invalid Actor ID.");
        }
        List<Movie> movies = movieRepo.findByActorsId(actorId);
        if (movies.isEmpty()) {
            throw new RuntimeException("No movies found for actor with ID " + actorId);
        }
        return movies;
    }

    @Override
    public List<Movie> getMoviesByActorName(String actorName) {
        if (!StringUtils.hasText(actorName)) {
            throw new IllegalArgumentException("Actor name cannot be empty.");
        }

        List<Movie> movies = movieRepo.getMoviesByActorName(actorName.trim());
        if (movies.isEmpty()) {
            throw new RuntimeException("No movies found for actor: " + actorName);
        }

        return movies;
    }

    @Override
    public List<Movie> getMoviesByGenre(String genre) {
        if (!StringUtils.hasText(genre)) {
            throw new IllegalArgumentException("Genre cannot be empty.");
        }

        List<Movie> movies = movieRepo.findByGenre(genre.trim());
        if (movies.isEmpty()) {
            throw new RuntimeException("No movies found in genre: " + genre);
        }

        return movies;
    }

    @Override
    public List<Movie> searchMoviesByTitle(String title) {
        if (!StringUtils.hasText(title)) {
            throw new IllegalArgumentException("Search title cannot be empty.");
        }

        List<Movie> movies = movieRepo.findByTitleContainingIgnoreCase(title.trim());
        if (movies.isEmpty()) {
            throw new RuntimeException("No movies found with title containing: " + title);
        }

        return movies;
    }

    @Override
    public List<Movie> sortMoviesByReleaseDate() {
        List<Movie> allMovies = movieRepo.findAll();
        if (allMovies.isEmpty()) {
            throw new RuntimeException("No movies to sort.");
        }

        return allMovies.stream()
                .sorted(Comparator.comparing(Movie::getReleaseDate).reversed())
                .collect(Collectors.toList());
    }
}