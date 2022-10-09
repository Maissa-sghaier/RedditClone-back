package com.example.Reddit_clone.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Reddit_clone.model.Post;
import com.example.Reddit_clone.model.Subreddit;
import com.example.Reddit_clone.model.User;
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

	List<Post> findAllBySubreddit(Subreddit subreddit);

	List<Post> findByUser(User user);
	

}
