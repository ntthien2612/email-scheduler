package com.example.emailscheduler.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.entity.Schedule;
import com.example.emailscheduler.repository.ScheduleRepository;

class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    public ScheduleServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSchedule() {
        EmailTemplate template = new EmailTemplate();
        template.setId(1L);

        Schedule schedule = new Schedule();
        schedule.setName("S1");
        schedule.setTemplate(template);
        schedule.setType(Schedule.Type.DAILY);
        schedule.setReceiverEmail("test@test.com");
        schedule.setStatus(Schedule.Status.ACTIVE);
        schedule.setCreatedAt(LocalDateTime.now());

        when(scheduleRepository.save(schedule)).thenReturn(schedule);
    }
}