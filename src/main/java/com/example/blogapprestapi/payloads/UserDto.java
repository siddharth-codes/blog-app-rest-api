package com.example.blogapprestapi.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4,message = "User name should be more than 4 characters")
    private String name;
    @NotNull
    @Email(message = "Email address is not valid" )
    private String email;
    @NotEmpty
    @Size(min = 4,max = 10,message = "password length should lie between 4 to 10")
    private String password;
    @NotNull
    private String about;

}
