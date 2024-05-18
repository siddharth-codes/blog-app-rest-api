package com.example.blogapprestapi.repositories;

import com.example.blogapprestapi.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CategoryRepo extends JpaRepository<Category,Integer> {
}
