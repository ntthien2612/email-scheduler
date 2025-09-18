package com.example.emailscheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.emailscheduler.entity.Schedule;
import com.example.emailscheduler.service.EmailTemplateService;
import com.example.emailscheduler.service.ScheduleService;
import com.example.emailscheduler.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final EmailTemplateService templateService;
    private final UserService userController;

    @GetMapping
    public String listSchedules(Model model) {
        model.addAttribute("schedules", scheduleService.findAll());
        return "schedule/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("schedule", new Schedule());
        model.addAttribute("templates", templateService.findAll());
        model.addAttribute("users", userController.findAll());
        return "schedule/form";
    }

    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("schedule") Schedule schedule,
                         BindingResult result,
                         @RequestParam Long templateId,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("templates", templateService.findAll());
            return "schedule/form";
        }
        scheduleService.create(schedule, templateId);
        return "redirect:/schedules";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.findById(id);
        model.addAttribute("schedule", schedule);
        model.addAttribute("templates", templateService.findAll());
        model.addAttribute("users", userController.findAll());
        return "schedule/form";
    }

    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("schedule") Schedule schedule,
                         BindingResult result,
                         @RequestParam Long templateId,
                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("templates", templateService.findAll());
            return "schedule/form";
        }
        scheduleService.update(id, schedule, templateId);
        return "redirect:/schedules";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        scheduleService.delete(id);
        return "redirect:/schedules";
    }
}
