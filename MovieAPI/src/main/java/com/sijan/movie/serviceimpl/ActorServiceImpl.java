package com.sijan.movie.serviceimpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sijan.movie.model.Actor;
import com.sijan.movie.repository.ActorRepository;
import com.sijan.movie.service.ActorService;

@Service
public class ActorServiceImpl implements ActorService {

    @Autowired
    private ActorRepository actorRepo;

    @Override
    public Actor saveActor(Actor actor) {
        boolean exists = actorRepo.existsByNameAndNationality(actor.getName(), actor.getNationality());
        if (exists) {
            throw new RuntimeException("An actor with the same name and nationality already exists.");
        }

        if (actor.getName() == null || actor.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Actor name cannot be empty.");
        }

        if (actor.getNationality() == null || actor.getNationality().trim().isEmpty()) {
            throw new IllegalArgumentException("Actor nationality cannot be empty.");
        }

        actor.setName(capitalizeFirstLetter(actor.getName()));
        actor.setNationality(capitalizeFirstLetter(actor.getNationality()));

        return actorRepo.save(actor);
    }

    @Override
    public Actor updateActor(Long id, Actor updatedActor) {
    	if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Actor ID.");
        }
        Actor existing = getActorById(id);

        if (updatedActor.getName() != null && !updatedActor.getName().trim().isEmpty()) {
            existing.setName(capitalizeFirstLetter(updatedActor.getName()));
        }

        if (updatedActor.getNationality() != null && !updatedActor.getNationality().trim().isEmpty()) {
            existing.setNationality(capitalizeFirstLetter(updatedActor.getNationality()));
        }

        if (updatedActor.getMovies() != null) {
            Set<Long> movieIds = new HashSet<>();
            updatedActor.getMovies().forEach(movie -> {
                if (!movieIds.add(movie.getId())) {
                    throw new RuntimeException("Duplicate movie detected in actor's list.");
                }
            });
            existing.setMovies(updatedActor.getMovies());
        }

        return actorRepo.save(existing);
    }

    @Override
    public void deleteActor(Long id) {
    	if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Actor ID.");
        }
        if (!actorRepo.existsById(id)) {
            throw new RuntimeException("Cannot delete. Actor with ID " + id + " does not exist.");
        }
        actorRepo.deleteById(id);
    }

    @Override
    public Actor getActorById(Long id) {
    	if (id == null || id < 0) {
            throw new IllegalArgumentException("Invalid Actor ID.");
        }
        return actorRepo.findById(id).orElseThrow(() ->new RuntimeException("Actor with ID " + id + " not found"));
    }

    @Override
    public List<Actor> getAllActors() {
        List<Actor> actors = actorRepo.findAll();
        if (actors.isEmpty()) {
            throw new RuntimeException("No actors found in the system.");
        }
        return actors;
    }

    @Override
    public List<Actor> getActorsByMovieId(Long movieId) {
    	if (movieId == null || movieId < 0) {
            throw new IllegalArgumentException("Invalid Movie ID.");
        }
        List<Actor> actors = actorRepo.getActorsByMovieId(movieId);
        if (actors.isEmpty()) {
            throw new RuntimeException("No actors found for movie ID " + movieId);
        }
        return actors;
    }

    @Override
    public List<Actor> searchActorsByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Search term cannot be empty.");
        }

        List<Actor> results = actorRepo.searchActorsByName(name.trim());
        if (results.isEmpty()) {
            throw new RuntimeException("No actors found matching: " + name);
        }
        return results;
    }

    @Override
    public List<Actor> getMostFeaturedActors() {
        List<Actor> results = actorRepo.getMostFeaturedActors();
        if (results.isEmpty()) {
            throw new RuntimeException("No featured actors found.");
        }
        return results;
    }

    private String capitalizeFirstLetter(String input) {
        input = input.trim();
        return Character.toUpperCase(input.charAt(0)) + input.substring(1).toLowerCase();
    }
}
