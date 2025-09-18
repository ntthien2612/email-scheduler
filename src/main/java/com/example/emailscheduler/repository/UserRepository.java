package com.example.emailscheduler.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.emailscheduler.entity.User;

public interface UserRepository extends JpaRepository<User, Long> { 
     Optional<User> findByEmail(String email);
     boolean existsByEmail(String email);
     Optional<User> findById(Long id);
     boolean existsById(Long id);
     void deleteById(Long id);
     List<User> findAll();
}
