package com.example.blogapprestapi.services.impl;

import com.example.blogapprestapi.entities.Comment;
import com.example.blogapprestapi.entities.Post;
import com.example.blogapprestapi.exceptions.ResourceNotFoundException;
import com.example.blogapprestapi.payloads.CommentDto;
import com.example.blogapprestapi.repositories.CommentRepo;
import com.example.blogapprestapi.repositories.PostRepo;
import com.example.blogapprestapi.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostRepo postRepo;
    @Override
    public CommentDto createComment(CommentDto commentDto, Integer postId) {
        Optional<Post> post = postRepo.findById(postId);
        if(post.isEmpty()){
            throw new ResourceNotFoundException("Post","id",postId);
        }
        Comment comment = dtoToComment(commentDto);
        comment.setPost(post.get());
        Comment comment1 =commentRepo.save(comment);
        return commentToDto(comment1);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Optional<Comment> comment = commentRepo.findById(commentId);
        if (comment.isEmpty()){
            throw new ResourceNotFoundException("Commnet","id",commentId);
        }
        commentRepo.delete(comment.get());

    }
    private Comment dtoToComment(CommentDto commentDto){
        return modelMapper.map(commentDto,Comment.class);
    }
    private CommentDto commentToDto(Comment comment){
        return modelMapper.map(comment,CommentDto.class);
    }
}
