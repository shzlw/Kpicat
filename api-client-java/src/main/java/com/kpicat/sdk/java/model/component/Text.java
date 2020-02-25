package com.kpicat.sdk.java.model.component;

public class Text {

    private String value;

    private int size;

    private String color;

    public Text(String value, int size, String color) {
        this.value = value;
        this.size = size;
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public int getSize() {
        return size;
    }

    public String getColor() {
        return color;
    }
}
