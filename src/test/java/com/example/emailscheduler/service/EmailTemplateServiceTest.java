package com.example.emailscheduler.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.repository.EmailTemplateRepository;

class EmailTemplateServiceTest {

    @Mock
    private EmailTemplateRepository templateRepository;

    @InjectMocks
    private EmailTemplateService templateService;

    public EmailTemplateServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateTemplate() {
        EmailTemplate template = new EmailTemplate();
        template.setName("T1");
        template.setSubject("Sub");
        template.setBody("<p>Body</p>");

        when(templateRepository.save(template)).thenReturn(template);

        EmailTemplate saved = templateService.create(template);
        assertThat(saved.getName()).isEqualTo("T1");

        verify(templateRepository, times(1)).save(template);
    }
}
