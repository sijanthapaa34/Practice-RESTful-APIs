package com.sijan.joblisting.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.sijan.joblisting.model.Post;

public interface PostRepository extends MongoRepository<Post, String>{

	
}
