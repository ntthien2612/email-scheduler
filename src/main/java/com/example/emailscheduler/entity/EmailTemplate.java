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
    @Column(nullable = false)
    private String body; // HTML or plain text

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist public void prePersist(){ createdAt = Instant.now(); }
    @PreUpdate  public void preUpdate(){ updatedAt = Instant.now(); }
}
