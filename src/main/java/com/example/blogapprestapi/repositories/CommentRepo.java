package com.example.blogapprestapi.repositories;

import com.example.blogapprestapi.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
}
