package com.example.Reddit_clone.mapper;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.Reddit_clone.dataTransferObject.SubredditDTO;
import com.example.Reddit_clone.model.Post;
import com.example.Reddit_clone.model.Subreddit;



@Mapper(componentModel = "spring")
public interface SubredditMapper {
	@Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))" )
	@Mapping(target = "id", source = "subredditId")
	SubredditDTO mapSubredditToDTO(Subreddit subreddit);
	
	default Integer mapPosts(List<Post> numberOfPosts) { 
		return numberOfPosts.size(); 
		}
	@InheritInverseConfiguration
	@Mapping(target = "posts", ignore = true)
	Subreddit mapDTOToSubreddit(SubredditDTO subredditDTO);
}
