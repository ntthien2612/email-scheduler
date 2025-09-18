package com.example.emailscheduler.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.emailscheduler.entity.EmailTemplate;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmailTemplateRepositoryTest {

    @Autowired
    private EmailTemplateRepository templateRepository;

    @Test
    void testSaveAndFindTemplate() {
        EmailTemplate template = new EmailTemplate();
        template.setName("Welcome");
        template.setSubject("Welcome Email");
        template.setBody("Hello, welcome to our system!");
        templateRepository.save(template);

        EmailTemplate found = templateRepository.findById(template.getId()).orElse(null);
        assertThat(found).isNotNull();
        assertThat(found.getName()).isEqualTo("Welcome");
    }
}