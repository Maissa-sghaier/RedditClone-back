package com.example.Reddit_clone.mapper;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.Reddit_clone.dataTransferObject.PostRequest;
import com.example.Reddit_clone.dataTransferObject.PostResponse;
import com.example.Reddit_clone.model.Post;
import com.example.Reddit_clone.model.Subreddit;
import com.example.Reddit_clone.model.User;
import com.example.Reddit_clone.model.Vote;
import com.example.Reddit_clone.model.VoteType;
import com.example.Reddit_clone.repository.CommentRepository;
import com.example.Reddit_clone.repository.VoteRepository;
import com.example.Reddit_clone.service.AuthService;
import com.github.marlonlom.utilities.timeago.TimeAgo;

import static com.example.Reddit_clone.model.VoteType.DOWNVOTE;
import static com.example.Reddit_clone.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private AuthService authService;
	
	@Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
	@Mapping(target = "description", source = "postRequest.description")
	@Mapping(target = "subreddit", source = "subreddit")
	@Mapping(target = "user", source = "user")
	@Mapping(target = "voteCount", constant = "0")
	public abstract Post mapDTOToPost(PostRequest postRequest, Subreddit subreddit , User user);
	
	@Mapping(target = "subredditName", source = "subreddit.name")
	@Mapping(target = "userName", source  = "user.username")
	@Mapping(target = "duration", expression = "java(getDuration(post))")
	@Mapping(target = "commentCount", expression = "java(commentCount(post))")
	@Mapping(target = "postId", source = "postId")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
	public abstract PostResponse mapPostToDTO(Post post);
	
	Integer commentCount(Post post) {
		return commentRepository.findByPost(post).size();
	}
	String getDuration(Post post) {
		return TimeAgo.using(post.getCreatedDate().toEpochMilli());
	}
	boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }
    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                    authService.getCurrentUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}
