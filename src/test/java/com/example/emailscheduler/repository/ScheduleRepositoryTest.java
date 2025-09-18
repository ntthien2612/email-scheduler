package com.example.emailscheduler.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.entity.Schedule;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ScheduleRepositoryTest {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private EmailTemplateRepository templateRepository;

    @Test
    void testSaveAndFindSchedule() {
        // Tạo template demo
        EmailTemplate template = new EmailTemplate();
        template.setName("Demo Template");
        template.setSubject("Demo Subject");
        template.setBody("Hello, this is a demo template.");
        templateRepository.save(template);

        // Tạo schedule demo
        Schedule schedule = new Schedule();
        schedule.setTemplate(template);
        schedule.setName("Daily Report");
        schedule.setType(Schedule.Type.DAILY);
        schedule.setCronExpression("0 0 9 * * ?");
        schedule.setReceiverEmail("ntthien.work.2612@gmail.com");
        schedule.setStatus(Schedule.Status.ACTIVE);
        schedule.setCreatedAt(LocalDateTime.now());
        schedule.setUpdatedAt(LocalDateTime.now());

        scheduleRepository.save(schedule);

        // Lấy lại schedule
        Schedule found = scheduleRepository.findById(schedule.getId()).orElse(null);

        // Assertions
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Daily Report");
        assertThat(found.getTemplate().getName()).isEqualTo("Demo Template");
        assertThat(found.getType()).isEqualTo(Schedule.Type.DAILY);
        assertThat(found.getStatus()).isEqualTo(Schedule.Status.ACTIVE);
    }
}