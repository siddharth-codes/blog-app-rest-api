package com.example.blogapprestapi.payloads;

import com.example.blogapprestapi.entities.Category;
import com.example.blogapprestapi.entities.User;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
    private String id;
    @Size(min = 4,max = 100,message = "title size should be between 4 and 100")
    @NotEmpty(message = "title cannot be empty")
    private String title;
    @Size(min = 4,max = 2000,message = "content size should be between 4 and 2000")
    @NotEmpty(message = "content cannot be empty")
    private String content;

    //other fields below are only to show the response and not required to be passed with POST request
    private String imageName;
    private Date addedDate;
    //for the below field we are using DTO because Dto do not have Post field , so it will not run in recursion
    private CategoryDto category;
    private UserDto user;
    private Set<CommentDto> comments;
}
