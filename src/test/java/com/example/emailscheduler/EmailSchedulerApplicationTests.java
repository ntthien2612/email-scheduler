package com.example.emailscheduler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.repository.EmailTemplateRepository;
import com.example.emailscheduler.service.EmailTemplateService;

class EmailTemplateServiceTest {

    @Mock
    private EmailTemplateRepository repository;

    @InjectMocks
    private EmailTemplateService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTemplate() {
        EmailTemplate template = new EmailTemplate();
        template.setName("Welcome");
        template.setSubject("Hello");
        template.setBody("Body");

        when(repository.save(template)).thenReturn(template);

        EmailTemplate saved = service.create(template);

        assertThat(saved.getName()).isEqualTo("Welcome");
        verify(repository, times(1)).save(template);
    }

    @Test
    void testUpdateTemplateSuccess() {
        EmailTemplate existing = new EmailTemplate();
        existing.setId(1L);
        existing.setName("Old");
        existing.setSubject("Old");
        existing.setBody("Old");

        EmailTemplate newData = new EmailTemplate();
        newData.setName("New");
        newData.setSubject("New");
        newData.setBody("New");

        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        EmailTemplate updated = service.update(1L, newData);

        assertThat(updated.getName()).isEqualTo("New");
        verify(repository).save(existing);
    }
}