package com.example.emailscheduler.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.service.EmailTemplateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller quản lý Email Template.
 * Bao gồm: CRUD, phân trang, preview template.
 */
@Controller
@RequestMapping("/templates")
@RequiredArgsConstructor
@Slf4j
public class EmailTemplateController {

    private final EmailTemplateService templateService;

    /**
     * Hiển thị danh sách template có phân trang.
     */
    @GetMapping
    public String listTemplates(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                Model model) {
        Page<EmailTemplate> templates = templateService.getAll(page, size);
        model.addAttribute("templates", templates);
        return "templates/list";
    }

    /**
     * Hiển thị form tạo mới template.
     */
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("template", new EmailTemplate());
        return "templates/form";
    }

    /**
     * Xử lý tạo mới template.
     */
    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("template") EmailTemplate template,
                         BindingResult result,
                         RedirectAttributes redirect,
                         Model model) {
        if (result.hasErrors()) {
            return "templates/form";
        }
        try {
            templateService.create(template);
            redirect.addFlashAttribute("success", "Template created successfully!");
            return "redirect:/templates";
        } catch (IllegalStateException e) {
            log.warn("Failed to create template: {}", e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "templates/form";
        }
    }

    /**
     * Hiển thị form chỉnh sửa template.
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("template", templateService.getById(id));
        return "templates/form";
    }

    /**
     * Xử lý cập nhật template.
     */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("template") EmailTemplate template,
                         BindingResult result,
                         RedirectAttributes redirect,
                         Model model) {
        if (result.hasErrors()) {
            return "templates/form";
        }
        try {
            templateService.update(id, template);
            redirect.addFlashAttribute("success", "Template updated successfully!");
            return "redirect:/templates";
        } catch (IllegalStateException e) {
            log.warn("Failed to update template {}: {}", id, e.getMessage());
            model.addAttribute("error", e.getMessage());
            return "templates/form";
        }
    }

    /**
     * Xóa template theo ID.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            templateService.delete(id);
            redirect.addFlashAttribute("success", "Template deleted successfully!");
        } catch (IllegalStateException e) {
            log.warn("Failed to delete template {}: {}", id, e.getMessage());
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/templates";
    }

    /**
     * Xem trước template (render nội dung HTML).
     */
    @GetMapping("/{id}/preview")
    public String preview(@PathVariable Long id, Model model) {
        model.addAttribute("template", templateService.getById(id));
        return "templates/preview";
    }
}
