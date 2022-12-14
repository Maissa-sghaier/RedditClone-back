package com.example.Reddit_clone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Reddit_clone.dataTransferObject.SubredditDTO;
import com.example.Reddit_clone.service.SubredditService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/subreddit")
@AllArgsConstructor
public class SubredditController {
	
	private final SubredditService subredditService; 
	
	@PostMapping
	public ResponseEntity createSubreddit (@RequestBody SubredditDTO subredditDTO) {
		return ResponseEntity.status(HttpStatus.CREATED).body(subredditService.saveSubredditDTO(subredditDTO));
	}
	
	@GetMapping
	public ResponseEntity<List<SubredditDTO>> getAllSubreddits() {
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getAllSubreddits());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<SubredditDTO> getSubreddit(@PathVariable Long id){
		return ResponseEntity.status(HttpStatus.OK).body(subredditService.getSubreddit(id));
	}
}
