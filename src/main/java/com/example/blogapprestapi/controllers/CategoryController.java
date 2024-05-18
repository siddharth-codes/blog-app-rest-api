package com.example.blogapprestapi.controllers;

import com.example.blogapprestapi.payloads.ApiResponse;
import com.example.blogapprestapi.payloads.CategoryDto;
import com.example.blogapprestapi.services.CategoryService;
import jakarta.validation.Valid;
import jdk.jfr.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/")
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.createCategory(categoryDto), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCatUsingId(@PathVariable("id") int id){
        return new ResponseEntity<>(categoryService.getCategoryUsingId(id),HttpStatus.FOUND);
    }
    @GetMapping("/")
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        return new ResponseEntity<>(categoryService.getAllCategories(),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("id") int id,@Valid @RequestBody CategoryDto categoryDto){
        return new ResponseEntity<>(categoryService.updateCategory(categoryDto,id),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable("id")int id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(new ApiResponse("Deleted the category",true),HttpStatus.OK);
    }

}
