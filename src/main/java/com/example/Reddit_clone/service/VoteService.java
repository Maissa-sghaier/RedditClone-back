package com.example.Reddit_clone.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Reddit_clone.dataTransferObject.VoteDTO;
import com.example.Reddit_clone.exception.SpringRedditException;
import com.example.Reddit_clone.model.Post;
import com.example.Reddit_clone.model.Vote;
import com.example.Reddit_clone.repository.PostRepository;
import com.example.Reddit_clone.repository.VoteRepository;

import lombok.AllArgsConstructor;

import static com.example.Reddit_clone.model.VoteType.UPVOTE;

@Service
@AllArgsConstructor
public class VoteService {
	private final VoteRepository voteRepository;
	private final PostRepository postRepository;
	private final AuthService authService;
	
	@Transactional
	public void vote(VoteDTO voteDTO) {
		Post post = postRepository.findById(voteDTO.getPostId()).orElseThrow(() -> new SpringRedditException("The post was not found"));
		Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, authService.getCurrentUser());
		
		if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDTO.getVoteType())) {
			throw new SpringRedditException("You have already " + voteDTO.getVoteType() +"'d for this post" );
		}
		
		if (UPVOTE.equals(voteDTO.getVoteType())) {
			post.setVoteCount(post.getVoteCount() + 1);
		} else {
			post.setVoteCount(post.getVoteCount() - 1);
		}
		
		voteRepository.save(mapDTOToVote(voteDTO, post));
		postRepository.save(post);
		
	}

	private Vote mapDTOToVote(VoteDTO voteDTO, Post post) {
		return Vote.builder().voteType(voteDTO.getVoteType()).post(post).user(authService.getCurrentUser()).build();
	}

}
