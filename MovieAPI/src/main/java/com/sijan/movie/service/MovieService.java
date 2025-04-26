package com.sijan.movie.service;

import java.util.List;

import com.sijan.movie.model.Movie;

public interface MovieService {
    Movie saveMovie(Movie movie);
    Movie updateMovie(Long id, Movie updatedMovie);
    void deleteMovie(Long id);
    Movie getMovieById(Long id);
    List<Movie> getAllMovies();

    Movie addActorsToMovie(Long movieId, List<Long> actorIds);
    List<Movie> getMoviesByActorId(Long actorId); 
    List<Movie> getMoviesByActorName(String actorName);
    List<Movie> getMoviesByGenre(String genre);
    List<Movie> searchMoviesByTitle(String titlePart);                          
    List<Movie> sortMoviesByReleaseDate();
	
}


