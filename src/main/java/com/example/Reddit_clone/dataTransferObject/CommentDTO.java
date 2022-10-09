package com.example.Reddit_clone.dataTransferObject;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	private long id;
	private long postId;
	private Instant createdDate;
	private String text;
	private String userName;
}
