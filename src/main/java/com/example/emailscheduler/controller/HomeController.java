package com.example.emailscheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController
 * Điều hướng người dùng đến trang chủ của hệ thống.
 */
@Controller
public class HomeController {

    /**
     * Render trang chủ.
     * @param model Model dùng để truyền dữ liệu ra view (hiện tại chưa dùng).
     * @return tên view "home"
     */
    @GetMapping("/")
    public String home(Model model) {
        // Có thể thêm attribute nếu cần hiển thị dữ liệu động
        return "home";
    }
}