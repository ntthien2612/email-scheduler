package com.example.emailscheduler.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.emailscheduler.entity.EmailTemplate;

public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> { 
    boolean existsById(Long id); 
    Optional<EmailTemplate> findByName(String name);
    List<EmailTemplate> findAll();
    Page<EmailTemplate> findAll(Pageable pageable);
    Optional<EmailTemplate> findById(Long id);
}
