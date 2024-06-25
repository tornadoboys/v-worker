package com.itheima.test.utils;

public class TimeEncoder {
    public static String encodeDate(int year, int month, int day) {
        return String.format("%04d%02d%02d000000", year, month, day);
    } // 编码具体的旬

    public static String encodeTenday(int year, int month, int tenday) {
        return String.format("%04d%02d4%1d000000", year, month, tenday);
    } //

    // 编码具体的周
    public static String encodeWeek(int year, int month, int week) {
        return String.format("%04d%02d5%1d000000", year, month, week);
    }

    // 编码日期范围
    public static String encodeDateRange(int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay) {
        return String.format("%04d%02d%02d%04d%02d%02d", startYear, startMonth, startDay, endYear, endMonth, endDay);
    } // 编码旬范围

    public static String encodeTendayRange(int startYear, int startMonth, int startTenday, int endYear, int endMonth, int endTenday) {
        return String.format("%04d%02d4%1d%04d%02d4%1d", startYear, startMonth, startTenday, endYear, endMonth, endTenday);
    }

    // 编码周范围
    public static String encodeWeekRange(int startYear, int startMonth, int startWeek, int endYear, int endMonth, int endWeek) {
        return String.format("%04d%02d5%1d%04d%02d5%1d", startYear, startMonth, startWeek, endYear, endMonth, endWeek);
    }

    public static void main(String[] args) {
        // 测试示例
        String date = encodeDate(2023, 5, 20);
        String tenday = encodeTenday(2023, 5, 2);
        String week = encodeWeek(2023, 5, 3);
        String dateRange = encodeDateRange(2023, 1, 1, 2023, 12, 31);
        String tendayRange = encodeTendayRange(2023, 1, 1, 2023, 3, 3);
        String weekRange = encodeWeekRange(2023, 1, 1, 2023, 4, 4);
        System.out.println("Specific Date: " + date);
        System.out.println("Specific Tenday: " + tenday);
        System.out.println("Specific Week: " + week);
        System.out.println("Date Range: " + dateRange);
        System.out.println("Tenday Range: " + tendayRange);
        System.out.println("Week Range: " + weekRange);
    }
}