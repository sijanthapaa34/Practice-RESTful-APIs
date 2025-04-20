package com.sijan.movie.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sijan.movie.model.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long>{

	 @Query("SELECT a FROM Actor a JOIN a.movies m WHERE m.id = :movieId")
	List<Actor> getActorsByMovieId(Long movieId);

	 @Query("SELECT a FROM Actor a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<Actor> searchActorsByName(String name);

	 @Query("SELECT a FROM Actor a JOIN a.movies m GROUP BY a.id ORDER BY COUNT(m.id) DESC")
	List<Actor> getMostFeaturedActors();

	 boolean existsByNameAndNationality(String name, String nationality);

}
