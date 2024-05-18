package com.example.blogapprestapi.services.impl;

import com.example.blogapprestapi.entities.Category;
import com.example.blogapprestapi.entities.Post;
import com.example.blogapprestapi.entities.User;
import com.example.blogapprestapi.exceptions.ResourceNotFoundException;
import com.example.blogapprestapi.payloads.PostDto;
import com.example.blogapprestapi.payloads.PostPaginationResponse;
import com.example.blogapprestapi.repositories.CategoryRepo;
import com.example.blogapprestapi.repositories.PostRepo;
import com.example.blogapprestapi.repositories.UserRepo;
import com.example.blogapprestapi.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("user","id",userId);
        }
        Optional<Category> category = categoryRepo.findById(categoryId);
        if(category.isEmpty()){
            throw new ResourceNotFoundException("category","id",categoryId);
        }
        Post post = this.dtoToPost(postDto);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(user.get());
        post.setCategory(category.get());
        return this.postToDto(postRepo.save(post));
    }

    @Override
    public PostDto updatePost(Integer postId, PostDto postDto) {
        Optional<Post> post = postRepo.findById(postId);
        if(post.isEmpty()){
            throw new ResourceNotFoundException("Post","id",postId);
        }
        post.get().setTitle(postDto.getTitle());
        post.get().setContent(postDto.getContent());
        if (postDto.getImageName() != null){
            post.get().setImageName(postDto.getImageName());
        }
        Post post1 = postRepo.save(post.get());
        return postToDto(post1);
    }

    @Override
    public PostDto getPostById(Integer postId) {
        Optional<Post> post = postRepo.findById(postId);
        if(post.isEmpty()){
            throw new ResourceNotFoundException("Post","id",postId);
        }
        return postToDto(post.get());
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> allPosts = postRepo.findAll();
        List<PostDto> allPostsDtos = allPosts.stream().map(e -> postToDto(e)).toList();
        return allPostsDtos;
    }

    @Override
    public void deletePostById(Integer postId) {
        Optional<Post> post = postRepo.findById(postId);
        if(post.isEmpty()){
            throw new ResourceNotFoundException("Post","id",postId);
        }
        postRepo.delete(post.get());
    }

    @Override
    public List<PostDto> getAllPostByUser(Integer userId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("user","id",userId);
        }
        List<Post> userPost = postRepo.findByUser(user.get());
        List<PostDto> userPostDto = userPost.stream().map(e->postToDto(e)).toList();
        return userPostDto;
    }

    @Override
    public List<PostDto> getAllPostByCategory(Integer categoryId) {
        Optional<Category> category = categoryRepo.findById(categoryId);
        if(category.isEmpty()){
            throw new ResourceNotFoundException("category","id",categoryId);
        }
        List<Post> posts = postRepo.findByCategory(category.get());
        List<PostDto> postDtos = posts.stream().map(e -> postToDto(e)).toList();
        return postDtos;
    }

    @Override
    public List<PostDto> searchPostByKeyWord(String keyword) {
        return null;
    }

    @Override
    public PostPaginationResponse getAllPostsPagination(Integer pageNumber, Integer pageSize,String sortBy) {
        Pageable p = PageRequest.of(pageNumber,pageSize, Sort.by(sortBy));
        Page<Post> pagePosts = postRepo.findAll(p);
        List<Post> posts = pagePosts.getContent();
        List<PostDto> postDtos = posts.stream().map(e -> postToDto(e)).toList();
        PostPaginationResponse postPaginationResponse = new PostPaginationResponse();
        postPaginationResponse.setContent(postDtos);
        postPaginationResponse.setPageNumber(pagePosts.getNumber());
        postPaginationResponse.setPageSize(pagePosts.getSize());
        postPaginationResponse.setTotalElements(pagePosts.getTotalElements());
        postPaginationResponse.setTotalPages(pagePosts.getTotalPages());
        postPaginationResponse.setLastPage(pagePosts.isLast());
        return postPaginationResponse;
    }

    @Override
    public List<PostDto> searchPostsByKeyword(String keyword) {
        List<Post> posts = postRepo.findByTitleContaining(keyword);
        return posts.stream().map(p -> postToDto(p)).toList();
    }

    private Post dtoToPost(PostDto postDto){
        return modelMapper.map(postDto,Post.class);
    }
    private PostDto postToDto(Post post){
        return modelMapper.map(post,PostDto.class);
    }
}
