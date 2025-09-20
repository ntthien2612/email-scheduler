package com.example.emailscheduler.service;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.repository.EmailTemplateRepository;
import com.example.emailscheduler.repository.ScheduleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service xử lý nghiệp vụ cho EmailTemplate.
 */
@Service
@RequiredArgsConstructor
public class EmailTemplateService {

    private final EmailTemplateRepository templateRepo;
    private final ScheduleRepository scheduleRepo;

    /**
     * Lấy danh sách template có phân trang.
     *
     * @param page trang (0-based)
     * @param size số item mỗi trang
     * @return Page<EmailTemplate>
     */
    @Transactional(readOnly = true)
    public Page<EmailTemplate> getAll(int page, int size) {
        return templateRepo.findAll(
                PageRequest.of(page, size, Sort.by("createdAt").descending())
        );
    }

    /**
     * Lấy tất cả template (không phân trang).
     */
    @Transactional(readOnly = true)
    public List<EmailTemplate> findAll() {
        return templateRepo.findAll();
    }

    /**
     * Tìm template theo id. Ném lỗi nếu không tồn tại.
     */
    @Transactional(readOnly = true)
    public EmailTemplate getById(Long id) {
        return templateRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("EmailTemplate not found with id: " + id));
    }

    /**
     * Tạo mới template.
     */
    @Transactional
    public EmailTemplate create(EmailTemplate template) {
        return templateRepo.save(template);
    }

    /**
     * Cập nhật template.
     */
    @Transactional
    public EmailTemplate update(Long id, EmailTemplate update) {
        EmailTemplate existing = getById(id);
        existing.setName(update.getName());
        existing.setSubject(update.getSubject());
        existing.setBody(update.getBody());
        return templateRepo.save(existing);
    }

    /**
     * Xóa template nếu không được sử dụng trong Schedule.
     */
    @Transactional
    public void delete(Long id) {
        if (scheduleRepo.existsByTemplateId(id)) {
            throw new IllegalStateException("Template id=" + id + " đang được sử dụng trong Schedule, không thể xóa.");
        }
        templateRepo.deleteById(id);
    }
}
