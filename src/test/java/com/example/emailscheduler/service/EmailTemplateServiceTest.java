package com.example.emailscheduler.service;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.repository.EmailTemplateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailTemplateServiceTest {

    @Mock
    private EmailTemplateRepository templateRepo;

    @InjectMocks
    private EmailTemplateService templateService;

    private EmailTemplate template;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        template = new EmailTemplate();
        template.setId(1L);
        template.setName("Daily Report");
        template.setSubject("Báo cáo ngày");
        template.setBody("<p>Nội dung báo cáo ngày</p>");
    }

    @Test
    void create_ShouldReturnSavedTemplate() {
        // Arrange
        when(templateRepo.save(any(EmailTemplate.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        EmailTemplate saved = templateService.create(template);

        // Assert
        assertThat(saved.getName()).isEqualTo("Daily Report");
        verify(templateRepo, times(1)).save(template);
    }

    @Test
    void update_ShouldReturnUpdatedTemplate() {
        // Arrange
        EmailTemplate updatedData = new EmailTemplate();
        updatedData.setName("Weekly Report");
        updatedData.setSubject("Báo cáo tuần");
        updatedData.setBody("<p>Nội dung báo cáo tuần</p>");

        when(templateRepo.findById(1L)).thenReturn(Optional.of(template));
        when(templateRepo.save(any(EmailTemplate.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        EmailTemplate updated = templateService.update(1L, updatedData);

        // Assert
        assertThat(updated.getName()).isEqualTo("Weekly Report");
        assertThat(updated.getSubject()).isEqualTo("Báo cáo tuần");
        verify(templateRepo, times(1)).save(template);
    }

    @Test
    void delete_ShouldCallRepositoryDelete() {
        // Act
        templateService.delete(1L);

        // Assert
        verify(templateRepo, times(1)).deleteById(1L);
    }

    @Test
    void getById_ShouldReturnTemplate_WhenExists() {
        // Arrange
        when(templateRepo.findById(1L)).thenReturn(Optional.of(template));

        // Act
        EmailTemplate found = templateService.getById(1L);

        // Assert
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(1L);
        verify(templateRepo, times(1)).findById(1L);
    }

    @Test
    void getAll_ShouldReturnPageOfTemplates() {
        // Arrange
        Page<EmailTemplate> page = new PageImpl<>(List.of(template));
        when(templateRepo.findAll(PageRequest.of(0, 5))).thenReturn(page);

        // Act
        Page<EmailTemplate> result = templateService.getAll(0, 5);

        // Assert
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Daily Report");
        verify(templateRepo, times(1)).findAll(PageRequest.of(0, 5));
    }
}