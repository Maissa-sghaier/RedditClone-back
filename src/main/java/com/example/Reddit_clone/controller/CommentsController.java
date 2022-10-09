package com.example.Reddit_clone.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Reddit_clone.dataTransferObject.CommentDTO;
import com.example.Reddit_clone.service.CommentsService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/comments")
public class CommentsController {
	private final CommentsService commentsService;
	
	@PostMapping
	public ResponseEntity<Void> createComment(@RequestBody CommentDTO commentDTO) {
			commentsService.saveComment(commentDTO);
			return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@GetMapping("/byPost/{postId}")
	public ResponseEntity<List<CommentDTO>> getAllCommentsInAPost(@PathVariable long postId) {
		return ResponseEntity.status(HttpStatus.OK).body(commentsService.getAllCommentsInAPost(postId));
	}
	
	@GetMapping("/byUser/{userName}")
	public ResponseEntity<List<CommentDTO>> getAllCommentsPerUser(@PathVariable String userName){
		return ResponseEntity.status(HttpStatus.OK).body(commentsService.getAllCommentsPerUser(userName));
	}
}
