package com.example.Reddit_clone.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Reddit_clone.dataTransferObject.VoteDTO;
import com.example.Reddit_clone.service.VoteService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/votes")
public class VoteController {
	private final VoteService voteService;
	
	@PostMapping
	public ResponseEntity<Void> vote(@RequestBody VoteDTO voteDTO){
		voteService.vote(voteDTO);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
