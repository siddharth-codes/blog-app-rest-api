package com.example.blogapprestapi.services.impl;

import com.example.blogapprestapi.entities.Role;
import com.example.blogapprestapi.entities.User;
import com.example.blogapprestapi.exceptions.ResourceNotFoundException;
import com.example.blogapprestapi.payloads.UserDto;
import com.example.blogapprestapi.repositories.RoleRepo;
import com.example.blogapprestapi.repositories.UserRepo;
import com.example.blogapprestapi.services.UserService;
import com.example.blogapprestapi.utils.Constants;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = this.UserDtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Optional<Role> role = roleRepo.findById(Constants.NORMAL_USER);
        if(role.isEmpty()){
            throw new ResourceNotFoundException("ROLE","id",Constants.NORMAL_USER);
        }
        user.getRoles().add(role.get());
        User newUser = userRepo.save(user);
        return this.UserToDto(newUser);
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.UserDtoToUser(userDto);
        return this.UserToDto(userRepo.save(user));
    }

    @Override
    public UserDto getUserById(Integer userId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("user","id",userId);
        }
        return this.UserToDto(user.get());
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("user","id",userId);
        }
        user.get().setName(userDto.getName());
        user.get().setEmail(userDto.getEmail());
        user.get().setPassword(userDto.getPassword());
        user.get().setAbout(userDto.getAbout());
        return this.UserToDto(userRepo.save(user.get()));
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepo.findAll();
        List<UserDto> userDtos = users.stream().map(user ->this.UserToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userId) {
        Optional<User> user = userRepo.findById(userId);
        if(user.isEmpty()){
            throw new ResourceNotFoundException("user","id",userId);
        }
        userRepo.delete(user.get());
    }
    private User UserDtoToUser(UserDto userDto){
          User user = modelMapper.map(userDto,User.class);
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
//        user.setAbout(userDto.getAbout());
        return user;
    }
    private UserDto UserToDto(User user){
        UserDto userDto = modelMapper.map(user,UserDto.class);
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
//        userDto.setAbout(user.getAbout());
        return userDto;
    }
}
