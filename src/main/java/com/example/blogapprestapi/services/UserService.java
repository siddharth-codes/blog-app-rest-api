package com.example.blogapprestapi.services;

import com.example.blogapprestapi.payloads.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto userDto);
    UserDto createUser(UserDto userDto);
    UserDto getUserById(Integer userId);
    UserDto updateUser(UserDto userDto,Integer userId);
    List<UserDto> getAllUsers();
    void deleteUser(Integer userId);

}
