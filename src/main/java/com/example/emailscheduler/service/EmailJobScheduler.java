package com.example.emailscheduler.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.emailscheduler.entity.Schedule;
import com.example.emailscheduler.repository.ScheduleRepository;
import com.example.emailscheduler.util.CronUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailJobScheduler {

    private final ScheduleRepository scheduleRepository;
    private final MailService mailService;

    @Scheduled(cron = "0 * * * * *") // chạy mỗi phút
    @Transactional
    public void runSchedules() {
        LocalDateTime now = LocalDateTime.now();
        List<Schedule> schedules = scheduleRepository.findByStatusWithTemplate(Schedule.Status.ACTIVE);

        for (Schedule s : schedules) {
            try {
                if (CronUtils.isMatch(s.getCronExpression(), now)) {
                    mailService.sendEmail(
                            s.getReceiverEmail(),
                            s.getTemplate().getSubject(),
                            s.getTemplate().getBody()
                    );
                    log.info("Scheduled email sent to {} using template '{}' at {}",
                            s.getReceiverEmail(), s.getTemplate().getName(), now);
                }
            } catch (Exception e) {
                log.error("Failed to send email to {} at {}. Reason: {}",
                        s.getReceiverEmail(), now, e.getMessage(), e);
            }
        }
    }
}