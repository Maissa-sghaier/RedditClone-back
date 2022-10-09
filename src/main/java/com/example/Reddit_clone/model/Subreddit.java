package com.example.Reddit_clone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.FetchType.LAZY;

import java.time.Instant;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data	
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subreddit {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long subredditId;
	@NotBlank(message = "Community name is required")
	private String name;
	@NotBlank(message = "Description is required")
	private String description;
	private Instant createdDate;
	@OneToMany(fetch = LAZY)
	private List<Post> posts;
	@ManyToOne(fetch = LAZY)
	private User user;
}
