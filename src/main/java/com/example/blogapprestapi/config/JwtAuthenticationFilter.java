package com.example.blogapprestapi.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@RequiredArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //1.get token- will be in format Bearer1625371621

        String requestToken = request.getHeader("Authorization");
        String userName = null;
        String token = null;
        if(requestToken != null && requestToken.startsWith("Bearer")){
            token = requestToken.substring(7);
            try {
                userName = jwtTokenHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                System.out.println("Unable to get Jwt token");
            }catch (ExpiredJwtException e){
                System.out.println("JWt token expired");
            }catch (MalformedJwtException e){
                System.out.println("invalid JWT");
            }
        }else{
            System.out.println("Jwt Token doesn't have bearer");
        }

        //2.once we get token, validate Token
        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if(this.jwtTokenHelper.validateToken(token,userDetails)){
                //set if valid set authentication
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }else{
                System.out.println("invalid JWT token");
            }
        }else{
            System.out.println("Username is null or security context is not null");
        }
        //this will go forward if the securitycontextholder is set or else it will throw the error
        filterChain.doFilter(request,response);
    }
}
