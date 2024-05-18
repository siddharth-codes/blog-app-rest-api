package com.example.blogapprestapi.services.impl;

import com.example.blogapprestapi.entities.Category;
import com.example.blogapprestapi.exceptions.ResourceNotFoundException;
import com.example.blogapprestapi.payloads.CategoryDto;
import com.example.blogapprestapi.repositories.CategoryRepo;
import com.example.blogapprestapi.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public CategoryDto createCategory( CategoryDto categoryDto) {
        Category cat = this.modelMapper.map(categoryDto,Category.class);
        Category addedCat = this.categoryRepo.save(cat);
        return this.categoryToDto(addedCat);
    }

    @Override
    public CategoryDto getCategoryUsingId(int id) {
        Optional<Category> category = categoryRepo.findById(id);
        if(category.isEmpty()){
            throw new ResourceNotFoundException("category","id",id);
        }
        return categoryToDto(category.get());
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, int id) {
        Optional<Category> category = categoryRepo.findById(id);
        if(category.isEmpty()){
            throw new ResourceNotFoundException("category","id",id);
        }
        category.get().setCategoryTitle(categoryDto.getCategoryTitle());
        category.get().setCategoryDescription(categoryDto.getCategoryDescription());
        Category cat = this.categoryRepo.save(category.get());
        return categoryToDto(cat);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtos = categories.stream().map(this::categoryToDto).toList();
        return categoryDtos;
    }

    @Override
    public void deleteCategory(int categoryId) {
        Optional<Category> category = categoryRepo.findById(categoryId);
        if(category.isEmpty()){
            throw new ResourceNotFoundException("category","id",categoryId);
        }
        categoryRepo.delete(category.get());
    }

    private Category categoryDtotoCategory(CategoryDto categoryDto){
        return modelMapper.map(categoryDto, Category.class);
    }
    private CategoryDto categoryToDto(Category category){
        return modelMapper.map(category, CategoryDto.class);
    }
}
