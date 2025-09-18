package com.example.emailscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EmailSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmailSchedulerApplication.class, args);
		System.out.println("\nApplication started successfully!");
        System.out.println("Open: http://localhost:8080/\n");
	}

}
