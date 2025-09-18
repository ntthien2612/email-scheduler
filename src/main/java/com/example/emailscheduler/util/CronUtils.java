package com.example.emailscheduler.util;

import com.example.emailscheduler.entity.Schedule;

public class CronUtils {

    public static String generateCron(Schedule.Type type, Integer dayOfWeek, Integer dayOfMonth) {
        return switch (type) {
            case DAILY   -> "0 0 9 * * ?";        // 9h sáng mỗi ngày
            case WEEKLY  -> String.format("0 0 9 ? * %s", mapDayOfWeek(dayOfWeek)); // 9h sáng theo thứ
            case MONTHLY -> String.format("0 0 9 %d * ?", dayOfMonth != null ? dayOfMonth : 1);
            case CRON    -> null; // user nhập trực tiếp
        };
    }

    // map từ số (1=Monday,..7=Sunday) sang chuỗi Quartz
    private static String mapDayOfWeek(Integer dayOfWeek) {
        if (dayOfWeek == null) return "MON";
        return switch (dayOfWeek) {
            case 1 -> "MON";
            case 2 -> "TUE";
            case 3 -> "WED";
            case 4 -> "THU";
            case 5 -> "FRI";
            case 6 -> "SAT";
            case 7 -> "SUN";
            default -> "MON";
        };
    }
}