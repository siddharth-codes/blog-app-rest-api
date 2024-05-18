package com.example.blogapprestapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BlogAppRestApiApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BlogAppRestApiApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(new BCryptPasswordEncoder().encode("testPassword"));
		System.out.println(new BCryptPasswordEncoder().encode("testPassword3ip"));
		System.out.println(new BCryptPasswordEncoder().encode("testPassword3"));
		System.out.println(new BCryptPasswordEncoder().encode("testPassword3"));
		System.out.println(new BCryptPasswordEncoder().encode("testPas"));
	}
}
