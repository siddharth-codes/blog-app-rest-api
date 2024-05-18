package com.example.blogapprestapi.services;

import com.example.blogapprestapi.entities.Post;
import com.example.blogapprestapi.payloads.PostDto;
import com.example.blogapprestapi.payloads.PostPaginationResponse;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto,Integer userId,Integer PostId);
    PostDto updatePost(Integer postId,PostDto postDto);
    PostDto getPostById(Integer postId);
    List<PostDto> getAllPosts();
    void deletePostById(Integer id);
    List<PostDto> getAllPostByUser(Integer userId);
    List<PostDto> getAllPostByCategory(Integer categoryId);
    List<PostDto> searchPostByKeyWord(String keyword);
    PostPaginationResponse getAllPostsPagination(Integer pageNumber, Integer pageSize, String sortBy);
    List<PostDto> searchPostsByKeyword(String keyword);
}
