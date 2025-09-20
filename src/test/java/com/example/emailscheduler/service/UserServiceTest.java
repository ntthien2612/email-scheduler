package com.example.emailscheduler.service;

import com.example.emailscheduler.entity.User;
import com.example.emailscheduler.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId(1L);
        user.setName("Nguyen Van A");
        user.setEmail("a@example.com");
    }

    @Test
    void create_ShouldReturnSavedUser() {
        // Arrange
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        User saved = userService.create(user);

        // Assert
        assertThat(saved.getName()).isEqualTo("Nguyen Van A");
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void update_ShouldReturnUpdatedUser() {
        // Arrange
        User updatedData = new User();
        updatedData.setName("Nguyen Van B");
        updatedData.setEmail("b@example.com");

        when(userRepo.findById(1L)).thenReturn(Optional.of(user));
        when(userRepo.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        User updated = userService.update(1L, updatedData);

        // Assert
        assertThat(updated.getName()).isEqualTo("Nguyen Van B");
        assertThat(updated.getEmail()).isEqualTo("b@example.com");
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        // Act
        userService.delete(1L);

        // Assert
        verify(userRepo, times(1)).deleteById(1L);
    }

    @Test
    void findById_ShouldReturnUser_WhenExists() {
        // Arrange
        when(userRepo.findById(1L)).thenReturn(Optional.of(user));

        // Act
        User found = userService.findById(1L);

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getEmail()).isEqualTo("a@example.com");
        verify(userRepo, times(1)).findById(1L);
    }

    @Test
    void findAll_ShouldReturnListOfUsers() {
        // Arrange
        when(userRepo.findAll()).thenReturn(List.of(user));

        // Act
        List<User> users = userService.findAll();

        // Assert
        assertThat(users).hasSize(1);
        assertThat(users.get(0).getName()).isEqualTo("Nguyen Van A");
        verify(userRepo, times(1)).findAll();
    }
}