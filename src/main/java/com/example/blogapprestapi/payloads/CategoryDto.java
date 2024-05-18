package com.example.blogapprestapi.payloads;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty(message = "title cannot be empty")
    @Size(min = 4 , max = 10,message = "title size should lie between 4 to 10 char")
    private String categoryTitle;
    @NotEmpty(message = "description cannot be empty")
    @Size(min = 4,message = "size should be greater than or equal to 4")
    private String categoryDescription;
}
