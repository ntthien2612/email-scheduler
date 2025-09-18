package com.example.emailscheduler.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.emailscheduler.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindUser() {
        User user = new User();
        user.setName("Test User");
        user.setEmail("testuser@example.com");
        user.setStatus(User.Status.ACTIVE);
        user.setCreatedAt(java.time.LocalDateTime.now());

        userRepository.save(user);

        User found = userRepository.findByEmail("testuser@example.com").orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Test User");
    }
}