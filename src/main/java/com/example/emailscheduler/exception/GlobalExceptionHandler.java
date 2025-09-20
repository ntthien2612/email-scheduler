package com.example.emailscheduler.exception;

import java.time.LocalDateTime;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Xử lý Exception chung
    @ExceptionHandler(Exception.class)
    public String handleGeneralException(Exception ex, Model model) { 
        model.addAttribute("error", "Lỗi hệ thống");
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("timestamp", LocalDateTime.now());
        return "error/500"; // file: templates/error/500.html
    }

    // Xử lý Entity not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleNotFound(ResourceNotFoundException ex, Model model) {
        model.addAttribute("timestamp", LocalDateTime.now());
        model.addAttribute("error", "Không tìm thấy tài nguyên");
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }
}