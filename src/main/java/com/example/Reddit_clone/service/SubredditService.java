package com.example.Reddit_clone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Reddit_clone.dataTransferObject.SubredditDTO;
import com.example.Reddit_clone.exception.SpringRedditException;
import com.example.Reddit_clone.mapper.SubredditMapper;
import com.example.Reddit_clone.model.Subreddit;
import com.example.Reddit_clone.repository.SubredditRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SubredditService {
	
	private final SubredditRepository subredditRepository;
	private final SubredditMapper subredditMapper;
	@Transactional
	public SubredditDTO saveSubredditDTO(SubredditDTO subredditDTO) {
		Subreddit savedSubreddit = subredditRepository.save(subredditMapper.mapDTOToSubreddit(subredditDTO));
		subredditDTO.setId(savedSubreddit.getSubredditId());
		return subredditDTO; 
	}
	

	@Transactional(readOnly = true)
	public List<SubredditDTO> getAllSubreddits() {
		return subredditRepository.findAll().stream().map(subredditMapper::mapSubredditToDTO).collect(Collectors.toList());
	}

	public SubredditDTO getSubreddit(Long id) {
		Subreddit subreddit = subredditRepository.findById(id).orElseThrow(()-> new SpringRedditException("No Subbreddit with this id was found"));
		return subredditMapper.mapSubredditToDTO(subreddit);
	}

}
