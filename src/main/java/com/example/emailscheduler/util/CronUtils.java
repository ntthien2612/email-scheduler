package com.example.emailscheduler.util;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

import com.example.emailscheduler.entity.Schedule;

public class CronUtils {

    /**
     * Generate cron expression tùy theo loại schedule
     * @param type loại schedule: DAILY, WEEKLY, MONTHLY, CRON
     * @param hour giờ (0-23)
     * @param minute phút (0-59), mặc định 0
     * @param dayOfWeek nếu WEEKLY (1=Monday..7=Sunday)
     * @param dayOfMonth nếu MONTHLY (1..31)
     */
    public static String generateCron(Schedule.Type type,
                                      Integer hour,
                                      Integer minute,
                                      String dayOfWeek,
                                      Integer dayOfMonth) {
        int h = (hour != null ? hour : 9);
        int m = (minute != null ? minute : 0);

        return switch (type) {
            case DAILY   -> String.format("0 %d %d * * *", m, h); 
            case WEEKLY  -> String.format("0 %d %d * * %s", m, h, dayOfWeek != null ? dayOfWeek : "MON"); 
            case MONTHLY -> String.format("0 %d %d %d * *", m, h, dayOfMonth != null ? dayOfMonth : 1);
            case CRON    -> null; // user nhập trực tiếp
        };
    }

    /**
     * Kiểm tra cron có khớp với thời điểm hiện tại không
     * @param cronExpression kiểu Spring (6 trường): sec min hour day month dayOfWeek
     * @param now LocalDateTime cần so khớp
     */
    public static boolean isMatch(String cronExpression, LocalDateTime now) {
        if (cronExpression == null || cronExpression.isBlank()) {
            return false;
        }

        String[] parts = cronExpression.trim().split("\\s+");
        if (parts.length != 6) {
            throw new IllegalArgumentException("Invalid cron expression: " + cronExpression);
        }

        int sec   = now.getSecond();
        int min   = now.getMinute();
        int hour  = now.getHour();
        int day   = now.getDayOfMonth();
        int month = now.getMonthValue();
        String dow = now.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase(); // MON,TUE,...

        return matchPart(parts[0], sec)
            && matchPart(parts[1], min)
            && matchPart(parts[2], hour)
            && matchPart(parts[3], day)
            && matchPart(parts[4], month)
            && matchPart(parts[5], dow);
    }

    // Hỗ trợ *, số cụ thể, và tên thứ (MON, TUE, ...)
    private static boolean matchPart(String expr, Object value) {
        if (expr.equals("*")) return true;

        String valStr = value.toString();

        // dayOfWeek có thể là MON, TUE...
        if (value instanceof String) {
            return expr.equalsIgnoreCase(valStr);
        }

        // số
        try {
            int intVal = Integer.parseInt(valStr);
            int exprVal = Integer.parseInt(expr);
            return intVal == exprVal;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}