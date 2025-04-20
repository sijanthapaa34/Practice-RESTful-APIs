package com.sijan.movie.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sijan.movie.model.Movie;
import com.sijan.movie.service.MovieService;

@RestController
@RequestMapping("/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieService.saveMovie(movie);
    }

    @PutMapping("/{id}")
    public Movie updateMovie(@PathVariable Long id, @RequestBody Movie updatedMovie) {
        return movieService.updateMovie(id, updatedMovie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
    }

    @GetMapping("/{id}")
    public Movie getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id);
    }

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }
    
    
    @PostMapping("/{movieId}/actors")
    public Movie addActorsToMovie(
            @PathVariable Long movieId,
            @RequestBody List<Long> actorIds) {
        return movieService.addActorsToMovie(movieId, actorIds);
    }

    @GetMapping("/actor/{actorId}")
    public List<Movie> getMoviesByActor(@PathVariable Long actorId) {
        return movieService.getMoviesByActorId(actorId);
    }

    @GetMapping("/genre/{genre}")
    public List<Movie> getMoviesByGenre(@PathVariable String genre) {
        return movieService.getMoviesByGenre(genre);
    }

    @GetMapping("/title")
    public List<Movie> searchMoviesByTitle(@RequestParam String title) {
        return movieService.searchMoviesByTitle(title);
    }

    @GetMapping("/recentMovies")
    public List<Movie> sortMoviesByReleaseDate() {
        return movieService.sortMoviesByReleaseDate();
    }
}
