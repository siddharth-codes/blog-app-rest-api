package com.example.blogapprestapi.controllers;

import com.example.blogapprestapi.entities.Post;
import com.example.blogapprestapi.payloads.ApiResponse;
import com.example.blogapprestapi.payloads.PostDto;
import com.example.blogapprestapi.payloads.PostPaginationResponse;
import com.example.blogapprestapi.services.FileService;
import com.example.blogapprestapi.services.PostService;
import jakarta.persistence.Access;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;
    @PostMapping("/user/{user_id}/category/{category_id}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable("user_id") Integer userId,@PathVariable("category_id") Integer categoryId,@Valid @RequestBody PostDto postDto){
        PostDto postDto1 = postService.createPost(postDto,userId,categoryId);
        return new ResponseEntity<>(postDto1, HttpStatus.CREATED);
    }
    @GetMapping("/user/{user_id}/posts")
    public ResponseEntity<List<PostDto>> getPostByUserId(@PathVariable Integer user_id){
        List<PostDto> posts = postService.getAllPostByUser(user_id);
        return new ResponseEntity<>(posts,HttpStatus.FOUND);
    }
    @GetMapping("/category/{category_id}/posts")
    public ResponseEntity<List<PostDto>> getPostByCatId(@PathVariable Integer category_id){
        List<PostDto> posts = postService.getAllPostByCategory(category_id);
        return new ResponseEntity<>(posts,HttpStatus.FOUND);
    }
    @GetMapping("/posts")
    public ResponseEntity<List<PostDto>> getAllPosts(){
        return new ResponseEntity<>(postService.getAllPosts(),HttpStatus.OK);
    }
    @GetMapping("/posts/{post_id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Integer post_id){
        return new ResponseEntity<>(postService.getPostById(post_id),HttpStatus.FOUND);
    }
    @DeleteMapping("/posts/{post_id}")
    public ResponseEntity<ApiResponse> deletePostById(@PathVariable Integer post_id){
        postService.deletePostById(post_id);
        return new ResponseEntity<>(new ApiResponse("Deleted Post",true),HttpStatus.OK);
    }
    @PutMapping("/posts/{post_id}")
    public ResponseEntity<PostDto> updatePostUsingId(@PathVariable Integer post_id,@Valid@RequestBody PostDto postDto){
        return new ResponseEntity<>(postService.updatePost(post_id,postDto),HttpStatus.OK);
    }
    @GetMapping("/posts/pagination")
    public ResponseEntity<PostPaginationResponse> getAllPostsPageination(@RequestParam(value =  "pageNumber",defaultValue = "10",required = false)Integer pageNumber,
                                                         @RequestParam(value =  "pageSize",defaultValue = "10",required = false)Integer pageSize,
                                                                         @RequestParam(value =  "sortBy",defaultValue = "id",required = false)String sortBy
    ){
        return new ResponseEntity<>(postService.getAllPostsPagination(pageNumber,pageSize,sortBy),HttpStatus.OK);
    }
    @GetMapping("/posts/search/{keyword}")
    public ResponseEntity<List<PostDto>> getAllPostByKeyword(@PathVariable("keyword")String keyword){
        return new ResponseEntity<>(postService.searchPostsByKeyword(keyword),HttpStatus.OK);
    }
    @PostMapping("/posts/image/upload/{post_id}")
    public ResponseEntity<PostDto> uploadImage(
            @RequestParam("image")MultipartFile image,
            @PathVariable("post_id")Integer post_id
            ) throws IOException {
        PostDto postDto = postService.getPostById(post_id);
        String fileName = this.fileService.uploadImage(path,image);
        postDto.setImageName(fileName);
        PostDto updatePost = postService.updatePost(post_id,postDto);
        return new ResponseEntity<>(updatePost,HttpStatus.OK);
    }
}
