package com.example.emailscheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
            BindingResult result, 
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "user/form";
        }
        try {
            userService.create(user);
            redirect.addFlashAttribute("success", "User added successfully!");
        } catch (IllegalArgumentException e) {
            result.rejectValue("email", "error.user", e.getMessage());
            return "user/form";
        }
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
            BindingResult result,
            RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "user/form";
        }
        try {
            userService.update(id, user);
            redirect.addFlashAttribute("success", "User updated successfully!");
        } catch (IllegalArgumentException e) {
            result.rejectValue("id", "error.user", e.getMessage());
            return "user/form";
        }
        return "redirect:/users";
    }

    /**
     * Xử lý xóa user theo ID.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id,
            RedirectAttributes redirect) {
        userService.delete(id);
        redirect.addFlashAttribute("success", "User deleted successfully!");
        return "redirect:/users";
    }
}