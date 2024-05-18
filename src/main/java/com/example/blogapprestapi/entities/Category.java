package com.example.blogapprestapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;

    @Column(name="title",length = 100,nullable = false)
    private String categoryTitle;

    @Column(name="description")
    @NotEmpty
    private String categoryDescription;
    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //mentioning the CASCADE type as all will delete all the post when the corresponding entity is deleted
    private List<Post> posts;
}
