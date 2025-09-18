package com.example.emailscheduler.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.emailscheduler.entity.User;
import com.example.emailscheduler.entity.User.Status;
import com.example.emailscheduler.repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User(1L, "Alice", "alice@gmail.com", Status.ACTIVE, null, null);
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User created = userService.create(user);

        assertThat(created.getName()).isEqualTo("Alice");
        verify(userRepository, times(1)).save(user);
    }
}
