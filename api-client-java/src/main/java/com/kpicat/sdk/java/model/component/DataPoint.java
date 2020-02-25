package com.kpicat.sdk.java.model.component;

public class DataPoint {

    private String x;

    private float y;

    private String color;

    public DataPoint() {}

    public DataPoint(String x, float y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
