package com.example.Reddit_clone.dataTransferObject;

import com.example.Reddit_clone.model.VoteType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {
	private VoteType voteType;
	private Long postId;
}
