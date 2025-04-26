package com.sijan.movie.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sijan.movie.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long>{

	List<Movie> findByActorsId(Long actorId);

	List<Movie> findByGenre(String genre); 

	List<Movie> findByTitleContainingIgnoreCase(String title);

	@Query("SELECT m FROM Movie m JOIN m.actors a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :actorName, '%'))")
	List<Movie> getMoviesByActorName(String actorName);

	boolean existsByTitleIgnoreCaseAndReleaseDate(String title, LocalDate releaseDate);

}
