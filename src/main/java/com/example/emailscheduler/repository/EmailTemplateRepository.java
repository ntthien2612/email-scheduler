package com.example.emailscheduler.repository;

import com.example.emailscheduler.entity.EmailTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository quản lý entity {@link EmailTemplate}.
 *
 * Kế thừa JpaRepository đã có sẵn CRUD:
 * - save, findById, findAll, deleteById...
 * - existsById, findAll(Pageable)...
 *
 * Bổ sung method custom cho EmailTemplate.
 */
public interface EmailTemplateRepository extends JpaRepository<EmailTemplate, Long> {

    /**
     * Tìm template theo tên.
     *
     * @param name tên template
     * @return Optional<EmailTemplate>
     */
    Optional<EmailTemplate> findByName(String name);
}
