package com.example.emailscheduler.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity đại diện cho bảng "users".
 * 
 * Chứa thông tin cơ bản của người dùng:
 * - Họ tên
 * - Email
 * - Trạng thái hoạt động
 * - Thời gian tạo & cập nhật
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE; // giá trị mặc định

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Gán thời gian tạo trước khi persist vào DB.
     */
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = status == null ? Status.ACTIVE : status; // fallback an toàn
    }

    /**
     * Cập nhật thời gian trước khi update entity.
     */
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

    /**
     * Trạng thái người dùng.
     */
    public enum Status {
        ACTIVE,
        INACTIVE
    }
}