package com.example.Reddit_clone.service;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Reddit_clone.dataTransferObject.PostRequest;
import com.example.Reddit_clone.dataTransferObject.PostResponse;
import com.example.Reddit_clone.exception.SpringRedditException;
import com.example.Reddit_clone.mapper.PostMapper;
import com.example.Reddit_clone.model.Post;
import com.example.Reddit_clone.model.Subreddit;
import com.example.Reddit_clone.model.User;
import com.example.Reddit_clone.repository.PostRepository;
import com.example.Reddit_clone.repository.SubredditRepository;
import com.example.Reddit_clone.repository.UserRepository;

import static java.util.stream.Collectors.toList;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PostService {
	private final PostRepository postRepository;
    private final SubredditRepository subredditRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final PostMapper postMapper;
    
	public void savePost(PostRequest postRequest) {
		Subreddit subreddit = subredditRepository.findByName(postRequest.getSubredditName()).orElseThrow(()-> new SpringRedditException(postRequest.getSubredditName()));
		postRepository.save(postMapper.mapDTOToPost(postRequest, subreddit, authService.getCurrentUser()));
	}

	 @Transactional(readOnly = true)
	 public List<PostResponse> getAllPosts() {
		 return postRepository.findAll().stream().map(postMapper::mapPostToDTO).collect(toList());
	 }
	 
	 @Transactional(readOnly = true)
	 public PostResponse getPost(Long id) {
		 Post post = postRepository.findById(id).orElseThrow(() -> new SpringRedditException(id.toString()));
	     return postMapper.mapPostToDTO(post);
	 }

	 @Transactional(readOnly = true)
	 public List<PostResponse> getPostsBySubreddit(Long subredditId) {
		 Subreddit subreddit = subredditRepository.findById(subredditId).orElseThrow(() -> new SpringRedditException(subredditId.toString()));
	     List<Post> posts = postRepository.findAllBySubreddit(subreddit);
	     return posts.stream().map(postMapper::mapPostToDTO).collect(toList());
	 }
	 
	 @Transactional(readOnly = true)
	 public List<PostResponse> getPostsByUsername(String username) {
	        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
	        return postRepository.findByUser(user).stream().map(postMapper::mapPostToDTO).collect(toList());
	    }
}
