package com.sijan.joblisting.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sijan.joblisting.model.Post;
import com.sijan.joblisting.repository.PostRepository;
import com.sijan.joblisting.repository.SearchRepository;

import jakarta.servlet.http.HttpServletResponse;


@RestController
@CrossOrigin("http://localhost:3000") //for connection 
public class PostController {

	@Autowired
	PostRepository repo;
	
	@Autowired
	SearchRepository srepo;
//
//	@RequestMapping(value = "/")
//	public void redirect(HttpServletResponse response) throws IOException {
//		response.sendRedirect("/swagger-ui.html");
//	}
	@GetMapping("/posts")
	public List<Post> getAllPost(){
		return repo.findAll();
	}
	@PostMapping("/post")
	public Post addPost(@RequestBody Post post) {
		return repo.save(post);
	}
	
	
	@GetMapping("/post/{text}")
	public List<Post> getPost(@PathVariable String text){
		return srepo.findByText(text);
	}
}
