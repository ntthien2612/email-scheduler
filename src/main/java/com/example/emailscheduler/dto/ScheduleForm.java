package com.example.emailscheduler.dto;

import com.example.emailscheduler.entity.Schedule;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleForm {
    private Long id;

    @NotNull(message = "Template is required")
    private Long templateId;

    @NotBlank(message = "Name is required")
    private String name;

    @NotNull(message = "Type is required")
    private Schedule.Type type; // DAILY/WEEKLY/MONTHLY/CRON (enum)

    // Giờ và phút (áp dụng cho DAILY, WEEKLY, MONTHLY)
    private Integer hour; // mặc định 9 giờ sáng

    private Integer minute; // mặc định 0 phút

    // Day of week
    private String dayOfWeek; // nếu type = WEEKLY, mặc định thứ Hai

    // Day of month (1–31) – chỉ dùng cho MONTHLY
    @Min(1) @Max(31)
    private Integer dayOfMonth = 1; // nếu type = MONTHLY, mặc định ngày mùng 1

    // Nếu type = CRON thì dùng field này
    private String cronExpression;

    @NotBlank(message = "Receiver email is required")
    @Email(message = "Invalid email format")
    private String receiverEmail;

    private Schedule.Status status = Schedule.Status.ACTIVE;
}
