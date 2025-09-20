package com.example.emailscheduler.service;

import com.example.emailscheduler.dto.ScheduleForm;
import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.entity.Schedule;
import com.example.emailscheduler.repository.EmailTemplateRepository;
import com.example.emailscheduler.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepo;

    @Mock
    private EmailTemplateRepository templateRepo;

    @InjectMocks
    private ScheduleService scheduleService;

    private EmailTemplate template;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        template = new EmailTemplate();
        template.setId(1L);
        template.setName("Test Template");
    }

    @Test
    void create_ShouldReturnSavedSchedule() {
        // Arrange
        ScheduleForm form = new ScheduleForm();
        form.setTemplateId(1L);
        form.setName("Daily Report");
        form.setType(Schedule.Type.DAILY);
        form.setReceiverEmail("test@example.com");
        form.setStatus(Schedule.Status.ACTIVE);

        when(templateRepo.findById(1L)).thenReturn(Optional.of(template));
        when(scheduleRepo.save(any(Schedule.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Schedule schedule = scheduleService.create(form);

        // Assert
        assertThat(schedule.getName()).isEqualTo("Daily Report");
        assertThat(schedule.getTemplate()).isEqualTo(template);
        verify(scheduleRepo, times(1)).save(any(Schedule.class));
    }

    @Test
    void update_ShouldUpdateAndReturnSchedule() {
        // Arrange
        Schedule existing = new Schedule();
        existing.setId(1L);
        existing.setName("Old Schedule");
        existing.setTemplate(template);

        ScheduleForm form = new ScheduleForm();
        form.setTemplateId(1L);
        form.setName("Updated Schedule");
        form.setType(Schedule.Type.WEEKLY);
        form.setReceiverEmail("user@example.com");
        form.setStatus(Schedule.Status.ACTIVE);

        when(scheduleRepo.findById(1L)).thenReturn(Optional.of(existing));
        when(templateRepo.findById(1L)).thenReturn(Optional.of(template));
        when(scheduleRepo.save(any(Schedule.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        Schedule updated = scheduleService.update(1L, form);

        // Assert
        assertThat(updated.getName()).isEqualTo("Updated Schedule");
        assertThat(updated.getType()).isEqualTo(Schedule.Type.WEEKLY);
        verify(scheduleRepo, times(1)).save(existing);
    }

    @Test
    void delete_ShouldCallRepository() {
        // Act
        scheduleService.delete(1L);

        // Assert
        verify(scheduleRepo, times(1)).deleteById(1L);
    }

    @Test
    void findById_ShouldReturnSchedule_WhenExists() {
        // Arrange
        Schedule schedule = new Schedule();
        schedule.setId(1L);
        when(scheduleRepo.findById(1L)).thenReturn(Optional.of(schedule));

        // Act
        Schedule result = scheduleService.findById(1L);

        // Assert
        assertThat(result.getId()).isEqualTo(1L);
        verify(scheduleRepo, times(1)).findById(1L);
    }

    @Test
    void findAll_ShouldReturnListOfSchedules() {
        // Arrange
        Schedule s1 = new Schedule();
        s1.setId(1L);
        when(scheduleRepo.findAll()).thenReturn(List.of(s1));

        // Act
        List<Schedule> schedules = scheduleService.findAll();

        // Assert
        assertThat(schedules).hasSize(1);
        assertThat(schedules.get(0).getId()).isEqualTo(1L);
        verify(scheduleRepo, times(1)).findAll();
    }
}