package com.example.Reddit_clone.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import static javax.persistence.GenerationType.IDENTITY;

import java.time.Instant;

import static javax.persistence.FetchType.LAZY;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private long commentId;
	@NotEmpty
	private String text;
	private Instant createdDate;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "postId", referencedColumnName ="postId")
	private Post post;
	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

}
