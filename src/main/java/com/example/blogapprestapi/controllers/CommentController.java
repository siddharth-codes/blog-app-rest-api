package com.example.blogapprestapi.controllers;

import com.example.blogapprestapi.entities.Comment;
import com.example.blogapprestapi.payloads.ApiResponse;
import com.example.blogapprestapi.payloads.CommentDto;
import com.example.blogapprestapi.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/post/{post_id}/comment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer post_id){
        CommentDto commentDto1 = commentService.createComment(commentDto,post_id);
        return new ResponseEntity<>(commentDto1, HttpStatus.CREATED);
    }
    @DeleteMapping("/comment/{comment_id}")
    public ResponseEntity<?> deleteComment(@PathVariable Integer comment_id){
        commentService.deleteComment(comment_id);
        return new ResponseEntity<>(new ApiResponse("Deleted the comment",true),HttpStatus.OK);
    }
}
