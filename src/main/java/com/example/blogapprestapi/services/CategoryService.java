package com.example.blogapprestapi.services;

import com.example.blogapprestapi.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(CategoryDto categoryDto);
    CategoryDto getCategoryUsingId(int id);
    CategoryDto updateCategory(CategoryDto categoryDto,int id);
    List<CategoryDto> getAllCategories();
    void deleteCategory(int id);
}
