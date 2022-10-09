package com.example.Reddit_clone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.Reddit_clone.dataTransferObject.CommentDTO;
import com.example.Reddit_clone.model.Comment;
import com.example.Reddit_clone.model.Post;
import com.example.Reddit_clone.model.User;

@Mapper(componentModel = "spring")
public interface CommentMapper {
	@Mapping(target = "commentId", ignore = true)
    @Mapping(target = "text", source = "commentDTO.text")
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Comment mapDTOToComment(CommentDTO commentDTO, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "userName", expression = "java(comment.getUser().getUsername())")
    CommentDTO mapCommentToDTO(Comment comment);
}
