package com.example.emailscheduler.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.repository.EmailTemplateRepository;
import com.example.emailscheduler.repository.ScheduleRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailTemplateService {
    private final EmailTemplateRepository templateRepo;
    private final ScheduleRepository scheduleRepo;

    public Page<EmailTemplate> getAll(int page, int size) {
        return templateRepo.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending()));
    }
    
    public List<EmailTemplate> findAll() {
        return templateRepo.findAll();
    }

    public EmailTemplate getById(Long id) {
        return templateRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Template not found"));
    }

    public EmailTemplate create(EmailTemplate template) {
        return templateRepo.save(template);
    }

    public EmailTemplate update(Long id, EmailTemplate update) {
        EmailTemplate t = getById(id);
        t.setName(update.getName());
        t.setSubject(update.getSubject());
        t.setBody(update.getBody());
        return templateRepo.save(t);
    }

    public void delete(Long id) {
        boolean inUse = scheduleRepo.existsByTemplateId(id);
        if (inUse) throw new IllegalStateException("Template đang được sử dụng trong Schedule, không thể xóa.");
        templateRepo.deleteById(id);
    }
}
