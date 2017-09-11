package com.example.skatespots.models.Dao;

import com.example.skatespots.models.users.userBasic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<userBasic, Integer> {

    userBasic findByUsername(String username);
}
