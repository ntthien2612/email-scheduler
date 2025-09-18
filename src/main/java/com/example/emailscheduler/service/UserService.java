package com.example.emailscheduler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.emailscheduler.entity.User;
import com.example.emailscheduler.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
    }

    @Transactional
    public User create(User user) {
        // email unique check
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> { throw new IllegalArgumentException("Email already exists"); });
        return userRepository.save(user);
    }

    @Transactional
    public User update(Long id, User newUser) {
        User existing = findById(id);

        if (!existing.getEmail().equals(newUser.getEmail())) {
            userRepository.findByEmail(newUser.getEmail())
                    .ifPresent(u -> { throw new IllegalArgumentException("Email already exists"); });
        }

        existing.setName(newUser.getName());
        existing.setEmail(newUser.getEmail());
        existing.setStatus(newUser.getStatus());

        return userRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
