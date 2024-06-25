package com.itheima.test.entity;

public enum GFBand {
    BLUE("Blue", 1),              // 蓝光波段
    GREEN("Green", 2),            // 绿光波段
    RED("Red", 3),                // 红光波段
    NIR("Near Infrared", 4);      // 近红外波段

    private final String description;
    private final int order;

    GFBand(String description, int order) {
        this.description = description;
        this.order = order;
    }

    public String getDescription() {
        return description;
    }

    public int getOrder() {
        return order;
    }
    public static int getOrderByDescription(String description) {
        for (GFBand band : values()) {
            if (band.getDescription().equalsIgnoreCase(description)) {
                return band.getOrder();
            }
        }
        throw new IllegalArgumentException("No band with description: " + description);
    }
    public static void main(String[] args) {
        for (GFBand band : GFBand.values()) {
            System.out.println("Band " + band.name() +
                    " - Description: " + band.getDescription() +
                    ", Order: " + band.getOrder());
        }
    }
}
