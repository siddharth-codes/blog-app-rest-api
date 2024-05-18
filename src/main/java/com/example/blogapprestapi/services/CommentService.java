package com.example.blogapprestapi.services;

import com.example.blogapprestapi.payloads.CommentDto;

public interface CommentService{
    CommentDto createComment(CommentDto commentDto,Integer postId);
    void deleteComment(Integer commentId);
}
