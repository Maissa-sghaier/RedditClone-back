package com.example.Reddit_clone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Reddit_clone.model.Post;
import com.example.Reddit_clone.model.User;
import com.example.Reddit_clone.model.Vote;
@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
	Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);
}
