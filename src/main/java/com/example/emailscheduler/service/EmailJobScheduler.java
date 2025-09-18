package com.example.emailscheduler.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.emailscheduler.entity.Schedule;
import com.example.emailscheduler.repository.ScheduleRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailJobScheduler {

    private final ScheduleRepository scheduleRepository;
    private final MailService mailService;

    @Scheduled(cron = "0 * * * * *") 
    @Transactional
    public void runSchedules() {
        List<Schedule> schedules = scheduleRepository.findByStatusWithTemplate(Schedule.Status.ACTIVE);
        for (Schedule s : schedules) {
            try {
                mailService.sendEmail(
                        s.getReceiverEmail(),
                        s.getTemplate().getSubject(),
                        s.getTemplate().getBody()
                );
                System.out.println("Sent email to " + s.getReceiverEmail() + " at " + LocalDateTime.now());
            } catch (Exception e) {
                System.err.println("Failed to send email: " + e.getMessage());
            }
        }
    }
}