package com.sijan.movie.service;

import java.util.List;

import com.sijan.movie.model.Actor;

public interface ActorService {
    Actor saveActor(Actor actor);
    Actor updateActor(Long id, Actor updatedActor);
    void deleteActor(Long id);
    Actor getActorById(Long id);
    List<Actor> getAllActors();

    List<Actor> getActorsByMovieId(Long movieId);        
    List<Actor> searchActorsByName(String name);        
    List<Actor> getMostFeaturedActors();                  
}

