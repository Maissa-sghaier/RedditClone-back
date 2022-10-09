package com.example.Reddit_clone.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.Reddit_clone.dataTransferObject.CommentDTO;
import com.example.Reddit_clone.exception.SpringRedditException;
import com.example.Reddit_clone.mapper.CommentMapper;
import com.example.Reddit_clone.model.Comment;
import com.example.Reddit_clone.model.NotificationEmail;
import com.example.Reddit_clone.model.Post;
import com.example.Reddit_clone.model.User;
import com.example.Reddit_clone.repository.CommentRepository;
import com.example.Reddit_clone.repository.PostRepository;
import com.example.Reddit_clone.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CommentsService {
	private final PostRepository postRepository;
	private final UserRepository userRepository;
	private final AuthService authService;
	private final CommentMapper commentMapper;
	private final CommentRepository commentRepository;
	private final MailContentBuilderService mailContentBuilderService;
	private final MailService mailService;
	
	public void saveComment(CommentDTO commentDTO) {
		Post post = postRepository.findById(commentDTO.getPostId()).orElseThrow(() -> new SpringRedditException("The post was not found"));
		Comment comment = commentMapper.mapDTOToComment(commentDTO, post, authService.getCurrentUser());
		commentRepository.save(comment);
		String emailMessage = mailContentBuilderService.build(post.getUser().getUsername() + " posted a comment on your post ");
		sendCommentNotification(emailMessage, post.getUser());
	}

	private void sendCommentNotification(String emailMessage, User user) {
		mailService.sendMail(new NotificationEmail("New comment", user.getEmail(), emailMessage));
	}

	public List<CommentDTO> getAllCommentsInAPost(long postId) {
		Post post = postRepository.findById(postId).orElseThrow(()-> new SpringRedditException("Post not Found "));
		return commentRepository.findByPost(post).stream().map(commentMapper::mapCommentToDTO).collect(Collectors.toList());
		}
	
	public List<CommentDTO> getAllCommentsPerUser(String userName) {
		User user = userRepository.findByUsername(userName).orElseThrow(()-> new UsernameNotFoundException(userName));
		return commentRepository.findAllByUser(user).stream().map(commentMapper::mapCommentToDTO).collect(Collectors.toList());
	}
}
