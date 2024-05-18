package com.example.blogapprestapi.repositories;

import com.example.blogapprestapi.entities.Category;
import com.example.blogapprestapi.entities.Post;
import com.example.blogapprestapi.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepo extends JpaRepository<Post,Integer> {
    //here we can define our custom method if we want
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String keyword);
}
