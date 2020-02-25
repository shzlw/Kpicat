package com.kpicat.sdk.java.model.component;

import java.util.ArrayList;
import java.util.List;

public class PieChart extends Component {

    private static final String PIE_CHART = "pieChart";

    private int height;

    private List<DataPoint> points = new ArrayList<>();

    public PieChart(String backgroundColor, int height) {
        super(PIE_CHART);
        setBackgroundColor(backgroundColor);
        this.height = height;
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
