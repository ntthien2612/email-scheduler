package com.example.emailscheduler.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.emailscheduler.entity.EmailTemplate;
import com.example.emailscheduler.service.EmailTemplateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/templates")
@RequiredArgsConstructor
public class EmailTemplateController {

    private final EmailTemplateService templateService;

    // Danh sách + phân trang
    @GetMapping
    public String listTemplates(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                Model model) {
        Page<EmailTemplate> templates = templateService.getAll(page, size);
        model.addAttribute("templates", templates);
        return "templates/list";
    }

    // Form tạo mới
    @GetMapping("/create")
    public String createForm(Model model) {
        model.addAttribute("template", new EmailTemplate());
        return "templates/form";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("template") EmailTemplate template,
                         BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) return "templates/form";
        templateService.create(template);
        redirect.addFlashAttribute("success", "Tạo mới template thành công!");
        return "redirect:/templates";
    }

    // Form cập nhật
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("template", templateService.getById(id));
        return "templates/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("template") EmailTemplate template,
                         BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) return "templates/form";
        templateService.update(id, template);
        redirect.addFlashAttribute("success", "Cập nhật template thành công!");
        return "redirect:/templates";
    }

    // Xóa
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        try {
            templateService.delete(id);
            redirect.addFlashAttribute("success", "Xóa template thành công!");
        } catch (IllegalStateException e) {
            redirect.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/templates";
    }

    // Preview
    @GetMapping("/{id}/preview")
    public String preview(@PathVariable Long id, Model model) {
        model.addAttribute("template", templateService.getById(id));
        return "templates/preview";
    }
}
