package com.itheima.test.entity;

public enum Sentinel2Band {
    B1("Coastal aerosol", 1),
    B2("Blue", 2),
    B3("Green", 3),
    B4("Red", 4),
    B5("Vegetation Red Edge 1", 5),
    B6("Vegetation Red Edge 2", 6),
    B7("Vegetation Red Edge 3", 7),
    B8("NIR", 8),
    B8A("Narrow NIR", 9),
    B9("Water vapour", 10),
    B10("SWIR 1", 11),
    B11("SWIR 2", 12),
    B12("SWIR 3", 13);

    private final String description;
    private final int order;

    Sentinel2Band(String description, int order) {
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
        for (Sentinel2Band band : values()) {
            if (band.getDescription().equalsIgnoreCase(description)) {
                return band.getOrder();
            }
        }
        throw new IllegalArgumentException("No band with description: " + description);
    }
    public static void main(String[] args) {
        for (Sentinel2Band band : Sentinel2Band.values()) {
            System.out.println("Band " + band.name() +
                    " - Description: " + band.getDescription() +
                    ", Order: " + band.getOrder());
        }
    }
}