package com.itheima.test.utils;

public class TimeDecoder {

    public static String decode(String code) {
        if (code.endsWith("00000000")) {
            return decodeSpecificTime(code.substring(0, 8));
        } else {
            return decodeRangeOrUnitRange(code);
        }
    }

    private static String decodeSpecificTime(String code) {
        int year = Integer.parseInt(code.substring(0, 4));
        int month = Integer.parseInt(code.substring(4, 6));
        int dayOrUnitType = Integer.parseInt(code.substring(6, 7));
        int unitNumber = Integer.parseInt(code.substring(7, 8));
        switch (dayOrUnitType) {
            case 4:
                return String.format("%d年%d月的第%d旬", year, month, unitNumber);
            case 5:
                return String.format("%d年%d月的第%d周", year, month, unitNumber);
            default:
                return String.format("%d年%d月%d日", year, month, unitNumber);
        }
    }

    private static String decodeRangeOrUnitRange(String code) {
        int startYear = Integer.parseInt(code.substring(0, 4));
        int startMonth = Integer.parseInt(code.substring(4, 6));
        int startType = Integer.parseInt(code.substring(6, 7));
        int startUnit = Integer.parseInt(code.substring(7, 8));
        int endYear = Integer.parseInt(code.substring(8, 12));
        int endMonth = Integer.parseInt(code.substring(12, 14));
        int endType = Integer.parseInt(code.substring(14, 15));
        int endUnit = Integer.parseInt(code.substring(15, 16));
        if (startType == endType && (startType == 4 || startType == 5)) {
            String unitType = startType == 4 ? "旬" : "周";
            return String.format("%d年%d月的第%d%s到%d年%d月的第%d%s", startYear, startMonth, startUnit, unitType, endYear, endMonth, endUnit, unitType);
        } else {
            return String.format("%d年%d月%d日到%d年%d月%d日", startYear, startMonth, startUnit, endYear, endMonth, endUnit);
        }
    }

    public static void main(String[] args) {
        // 测试示例
        String specificDay = "2013010100000000";
        String specificTenday = "2013044000000000";
        String specificWeek = "2013055000000000";
        String rangeDays = "2013010120141231";
        String rangeTendays = "2013044020140445";
        String rangeWeeks = "2013055020140555";
        System.out.println(decode(specificDay));
        // 2013年1月1日
        System.out.println(decode(specificTenday));
        // 2013年4月的第0旬
        System.out.println(decode(specificWeek));
        // 2013年5月的第0周
        System.out.println(decode(rangeDays));
        // 2013年1月1日到2014年12月31日
        System.out.println(decode(rangeTendays));
        // 2013年4月的第0旬到2014年4月的第5旬
        System.out.println(decode(rangeWeeks));
        // 2013年5月的第0周到2014年5月的第5周
    }
}

