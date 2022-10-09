package com.example.Reddit_clone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.springframework.lang.Nullable;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long postId;
	@NotBlank(message = "Post Name can't be empty")
	private String postName;
	@Nullable 
	private String url;
	@Nullable
	@Lob
	private String description;
	private Integer voteCount = 0;
	private Instant createdDate;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "subredditId", referencedColumnName = "subredditId")
	private Subreddit subreddit; 
}
