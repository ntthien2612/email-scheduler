package com.example.emailscheduler.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.emailscheduler.dto.ScheduleForm;
import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.entity.Schedule;
import com.example.emailscheduler.repository.EmailTemplateRepository;
import com.example.emailscheduler.repository.ScheduleRepository;
import com.example.emailscheduler.util.CronUtils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service xử lý nghiệp vụ liên quan đến Schedule.
 */
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepo;
    private final EmailTemplateRepository templateRepo;

    /**
     * Tạo mới một Schedule từ ScheduleForm.
     */
    public Schedule create(ScheduleForm scheduleForm) {
        EmailTemplate template = getTemplate(scheduleForm.getTemplateId());
        Schedule schedule = new Schedule();
        applyFormToSchedule(scheduleForm, schedule, template);
        return scheduleRepo.save(schedule);
    }

    /**
     * Cập nhật Schedule có sẵn theo id.
     */
    public Schedule update(Long id, ScheduleForm scheduleForm) {
        Schedule schedule = scheduleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + id));

        EmailTemplate template = getTemplate(scheduleForm.getTemplateId());
        applyFormToSchedule(scheduleForm, schedule, template);

        return scheduleRepo.save(schedule);
    }

    /**
     * Xóa Schedule theo id.
     */
    public void delete(Long id) {
        scheduleRepo.deleteById(id);
    }

    /**
     * Tìm Schedule theo id.
     */
    public Schedule findById(Long id) {
        return scheduleRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + id));
    }

    /**
     * Lấy tất cả Schedule.
     */
    public List<Schedule> findAll() {
        return scheduleRepo.findAll();
    }

    // ================== PRIVATE UTILS ==================

    /**
     * Tìm template theo id, ném lỗi nếu không tồn tại.
     */
    private EmailTemplate getTemplate(Long templateId) {
        return templateRepo.findById(templateId)
                .orElseThrow(() -> new EntityNotFoundException("Template not found with id: " + templateId));
    }

    /**
     * Map dữ liệu từ ScheduleForm sang Schedule entity.
     */
    private void applyFormToSchedule(ScheduleForm form, Schedule schedule, EmailTemplate template) {
        schedule.setName(form.getName());
        schedule.setType(form.getType());
        schedule.setReceiverEmail(form.getReceiverEmail());
        schedule.setStatus(form.getStatus());
        schedule.setTemplate(template);

        if (form.getType() != Schedule.Type.CRON) {
            String cron = CronUtils.generateCron(
                    form.getType(),
                    form.getHour(),
                    form.getMinute(),
                    form.getDayOfWeek(),
                    form.getDayOfMonth()
            );
            schedule.setCronExpression(cron);
        } else {
            schedule.setCronExpression(form.getCronExpression());
        }
    }
}
