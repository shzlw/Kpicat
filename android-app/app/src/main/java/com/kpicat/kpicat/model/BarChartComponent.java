package com.kpicat.kpicat.model;

import java.util.ArrayList;
import java.util.List;

public class BarChartComponent extends RootComponent {

    private boolean xNumber;

    private int height;

    private List<DataPoint> points = new ArrayList<>();

    public BarChartComponent() {}

    public boolean isxNumber() {
        return xNumber;
    }

    public void setxNumber(boolean xNumber) {
        this.xNumber = xNumber;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<DataPoint> getPoints() {
        return points;
    }

    public void setPoints(List<DataPoint> points) {
        this.points = points;
    }
}
