package com.example.emailscheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.emailscheduler.entity.User;
import com.example.emailscheduler.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

/**
 * Controller chịu trách nhiệm xử lý các request liên quan đến User.
 * 
 * - Liệt kê danh sách user
 * - Tạo mới user
 * - Chỉnh sửa user
 * - Xóa user
 */
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Hiển thị danh sách tất cả người dùng.
     */
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "user/list";
    }

    /**
     * Hiển thị form tạo mới user.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user/form";
    }

    /**
     * Xử lý tạo mới user.
     * Nếu có lỗi validate → quay lại form, giữ lại dữ liệu.
     */
    @PostMapping("/new")
    public String create(
            @Valid @ModelAttribute("user") User user,
            BindingResult result) {
        if (result.hasErrors()) {
            return "user/form";
        }
        userService.create(user);
        return "redirect:/users";
    }

    /**
     * Hiển thị form chỉnh sửa user theo ID.
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "user/form";
    }

    /**
     * Xử lý cập nhật user theo ID.
     */
    @PostMapping("/{id}/edit")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("user") User user,
            BindingResult result) {
        if (result.hasErrors()) {
            return "user/form";
        }
        userService.update(id, user);
        return "redirect:/users";
    }

    /**
     * Xử lý xóa user theo ID.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        userService.delete(id);
        return "redirect:/users";
    }
}