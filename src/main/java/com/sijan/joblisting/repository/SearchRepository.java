package com.sijan.joblisting.repository;

import java.util.List;

import com.sijan.joblisting.model.Post;

public interface SearchRepository {

	List<Post> findByText(String text);
}
