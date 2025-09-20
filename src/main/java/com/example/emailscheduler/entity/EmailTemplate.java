package com.example.emailscheduler.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity đại diện cho bảng "email_templates".
 * 
 * Chứa thông tin mẫu email:
 * - Tên template
 * - Tiêu đề email
 * - Nội dung email (body)
 * - Thời gian tạo & cập nhật
 */
@Entity
@Table(name = "email_templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 200)
    private String subject;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    private Instant updatedAt;

    /**
     * Gán giá trị mặc định cho createdAt trước khi persist.
     */
    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }

    /**
     * Cập nhật giá trị updatedAt trước khi update.
     */
    @PreUpdate
    public void preUpdate() {
        updatedAt = Instant.now();
    }
}