package com.example.Reddit_clone.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Reddit_clone.model.Comment;
import com.example.Reddit_clone.model.Post;
import com.example.Reddit_clone.model.User;
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

	List<Comment> findByPost(Post post);

	List<Comment> findAllByUser(User user);

}
