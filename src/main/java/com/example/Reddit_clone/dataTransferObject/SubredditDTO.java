package com.example.Reddit_clone.dataTransferObject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubredditDTO {
	private Long id;
	private String name;
	private String description;
	private Integer numberOfPosts;
}
