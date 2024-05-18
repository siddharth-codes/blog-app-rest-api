package com.example.blogapprestapi.controllers;

import com.example.blogapprestapi.config.JwtTokenHelper;
import com.example.blogapprestapi.exceptions.AuthenticationException;
import com.example.blogapprestapi.payloads.JwtAuthRequest;
import com.example.blogapprestapi.payloads.JwtAuthResponse;
import com.example.blogapprestapi.payloads.UserDto;
import com.example.blogapprestapi.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth/")
public class AuthController {
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Autowired
    private UserDetailsService userDetailsService;
    //below AuhthenticationMnager has been declared bean in securityConfig
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest jwtAuthRequest
            ) throws Exception {
        authenticate(jwtAuthRequest.getUsername(),jwtAuthRequest.getPassword());
        UserDetails userByUsername = userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String token = jwtTokenHelper.generateToken(userByUsername);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(token);
        return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto){
        return  new ResponseEntity<>(userService.registerUser(userDto),HttpStatus.OK);
    }
    private void authenticate(String username,String password) throws Exception {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException e){
            throw new AuthenticationException("Invalid Username or PassWord");
        }

    }
}
