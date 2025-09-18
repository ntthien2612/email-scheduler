package com.example.emailscheduler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.entity.Schedule;
import com.example.emailscheduler.repository.EmailTemplateRepository;
import com.example.emailscheduler.repository.ScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final EmailTemplateRepository templateRepo;

    @Transactional
    public Schedule create(Schedule schedule, Long templateId) {
        EmailTemplate template = templateRepo.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));
        schedule.setTemplate(template);

        // nếu type != CRON thì tự sinh cron
        if (schedule.getType() != Schedule.Type.CRON) {
            schedule.setCronExpression(generateCron(schedule));
        }

        return scheduleRepo.save(schedule);
    }

    @Transactional
    public Schedule update(Long id, Schedule newSchedule, Long templateId) {
        Schedule existing = scheduleRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));

        EmailTemplate template = templateRepo.findById(templateId)
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));

        existing.setName(newSchedule.getName());
        existing.setType(newSchedule.getType());
        existing.setReceiverEmail(newSchedule.getReceiverEmail());
        existing.setStatus(newSchedule.getStatus());
        existing.setTemplate(template);

        if (existing.getType() != Schedule.Type.CRON) {
            existing.setCronExpression(generateCron(existing));
        } else {
            existing.setCronExpression(newSchedule.getCronExpression());
        }

        return scheduleRepo.save(existing);
    }

    public void delete(Long id) {
        scheduleRepo.deleteById(id);
    }

    public Schedule findById(Long id) {
        return scheduleRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Schedule not found"));
    }

    public List<Schedule> findAll() {
        return scheduleRepo.findAll();
    }

    // Dummy cron generator
    private String generateCron(Schedule schedule) {
        return switch (schedule.getType()) {
            case DAILY -> "0 0 9 * * ?"; // 9h sáng mỗi ngày
            case WEEKLY -> "0 0 9 ? * MON"; // 9h sáng thứ 2
            case MONTHLY -> "0 0 9 1 * ?"; // 9h sáng ngày 1 hàng tháng
            default -> schedule.getCronExpression();
        };
    }
}