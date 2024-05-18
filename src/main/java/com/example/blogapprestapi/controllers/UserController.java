package com.example.blogapprestapi.controllers;

import com.example.blogapprestapi.payloads.ApiResponse;
import com.example.blogapprestapi.payloads.UserDto;
import com.example.blogapprestapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserService userService;
    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        UserDto response = userService.createUser(userDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id")int id){
        UserDto userDto = userService.getUserById(id);
        return new ResponseEntity<>(userDto,HttpStatus.FOUND);
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id")int id,@Valid @RequestBody UserDto userDto){
        UserDto userDto1 = userService.updateUser(userDto,id);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable("id")int id){
        userService.deleteUser(id);
        return new ResponseEntity<>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
    }
    @GetMapping("/")
    public  ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userService.getAllUsers(),HttpStatus.OK);
    }
}
