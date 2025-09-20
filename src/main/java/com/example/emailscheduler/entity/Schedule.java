package com.example.emailscheduler.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Entity đại diện cho bảng "schedules".
 *
 * Chứa thông tin lịch gửi email:
 * - Liên kết với template
 * - Loại schedule (DAILY, WEEKLY, MONTHLY, CRON)
 * - Cron expression (nếu loại = CRON)
 * - Email người nhận
 * - Trạng thái (ACTIVE/INACTIVE)
 * - Thời gian tạo & cập nhật
 */
@Entity
@Table(name = "schedules")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Liên kết với EmailTemplate.
     * FetchType.LAZY để tối ưu performance (chỉ load khi cần).
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "template_id", nullable = false)
    private EmailTemplate template;

    @Column(nullable = false, length = 100)
    private String name;

    /**
     * Loại lịch gửi.
     */
    public enum Type {
        DAILY, WEEKLY, MONTHLY, CRON
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Type type;

    /**
     * Biểu thức CRON (chỉ dùng khi type = CRON).
     */
    @Column(length = 100)
    private String cronExpression;

    /**
     * Email người nhận.
     */
    @Column(nullable = false, length = 150)
    private String receiverEmail;

    /**
     * Trạng thái schedule.
     */
    public enum Status {
        ACTIVE, INACTIVE
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status = Status.ACTIVE;

    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * Gán thời gian tạo trước khi insert.
     */
    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
        status = status == null ? Status.ACTIVE : status; // fallback an toàn
    }

    /**
     * Cập nhật thời gian update trước khi update.
     */
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
