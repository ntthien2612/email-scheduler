package com.example.emailscheduler.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleForm {
    private Long id;
    private Long templateId;
    private String name;
    private String type; // DAILY/WEEKLY/MONTHLY/CRON
    private String time; // e.g. "09:00" or for weekly maybe day+time
    private String cronExpression; // when type=CRON
    private String receiverEmail;
    private String daysOfWeek; // optional, e.g. MON
    private String dayOfMonth; // optional
}
