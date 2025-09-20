package com.example.emailscheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.emailscheduler.dto.ScheduleForm;
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
    private final UserService userService;

    /**
     * Hiển thị danh sách tất cả Schedule.
     */
    @GetMapping
    public String listSchedules(Model model) {
        model.addAttribute("schedules", scheduleService.findAll());
        return "schedule/list";
    }

    /**
     * Hiển thị form tạo Schedule mới.
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        prepareFormModel(model, new ScheduleForm());
        return "schedule/form";
    }

    /**
     * Xử lý tạo mới Schedule.
     */
    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("scheduleForm") ScheduleForm scheduleForm,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirect) {
        validateCronExpression(scheduleForm, result);

        if (result.hasErrors()) {
            prepareFormModel(model, scheduleForm);
            return "schedule/form";
        }
        try {
            scheduleService.create(scheduleForm);
            redirect.addFlashAttribute("success", "Thêm Schedule thành công!");
            return "redirect:/schedules";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            prepareFormModel(model, scheduleForm);
            return "schedule/form";
        }
    }

    /**
     * Hiển thị form chỉnh sửa Schedule theo id.
     */
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Schedule schedule = scheduleService.findById(id);

        // map từ entity sang DTO
        ScheduleForm form = new ScheduleForm();
        form.setId(schedule.getId());
        form.setTemplateId(schedule.getTemplate().getId());
        form.setName(schedule.getName());
        form.setType(schedule.getType());
        form.setReceiverEmail(schedule.getReceiverEmail());
        form.setCronExpression(schedule.getCronExpression());
        form.setStatus(schedule.getStatus());

        prepareFormModel(model, form);
        return "schedule/form";
    }

    /**
     * Xử lý cập nhật Schedule.
     */
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("scheduleForm") ScheduleForm scheduleForm,
                         BindingResult result,
                         Model model,
                         RedirectAttributes redirect) {
        validateCronExpression(scheduleForm, result);

        if (result.hasErrors()) {
            prepareFormModel(model, scheduleForm);
            return "schedule/form";
        }
        try {
            scheduleService.update(id, scheduleForm);
            redirect.addFlashAttribute("success", "Schedule updated successfully!");
            return "redirect:/schedules";
        } catch (IllegalStateException e) {
            model.addAttribute("error", e.getMessage());
            prepareFormModel(model, scheduleForm);
            return "schedule/form";
        }
    }

    /**
     * Xóa Schedule theo id.
     */
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirect) {
        scheduleService.delete(id);
        redirect.addFlashAttribute("success", "Schedule deleted successfully!");
        return "redirect:/schedules";
    }

    /**
     * Hàm helper để validate Cron Expression nếu type là CRON.
     */
    private void validateCronExpression(ScheduleForm scheduleForm, BindingResult result) {
        if (scheduleForm.getType() == Schedule.Type.CRON) {
            String cron = scheduleForm.getCronExpression();
            String regex = "^([0-5]?\\d)\\s"            // second
                         + "([0-5]?\\d)\\s"            // minute
                         + "([01]?\\d|2[0-3])\\s"      // hour
                         + "((\\*|[1-9]|[12]\\d|3[01]))\\s" // day-of-month
                         + "(\\*|[1-9]|1[0-2])\\s"     // month
                         + "(\\*|MON|TUE|WED|THU|FRI|SAT|SUN)$"; // day-of-week

            if (cron == null || cron.isBlank() || !cron.matches(regex)) {
                result.rejectValue("cronExpression", "cronExpression.invalid",
                        "Invalid cron expression. Format: sec min hour day month day-of-week");
            }
        }
    }

    /**
     * Hàm helper để nạp dữ liệu dùng chung cho form (templates + users).
     */
    private void prepareFormModel(Model model, ScheduleForm scheduleForm) {
        model.addAttribute("scheduleForm", scheduleForm);
        model.addAttribute("templates", templateService.findAll());
        model.addAttribute("users", userService.findAll());
    }
}
