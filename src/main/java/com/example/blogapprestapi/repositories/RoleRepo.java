package com.example.blogapprestapi.repositories;

import com.example.blogapprestapi.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {

}
