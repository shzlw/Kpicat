package com.kpicat.kpicat.model;

import java.util.ArrayList;
import java.util.List;

public class PieChartComponent extends RootComponent {

    private int height;

    private List<DataPoint> points = new ArrayList<>();

    public PieChartComponent() {}

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
