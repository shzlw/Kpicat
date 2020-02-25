package com.kpicat.apiserver.model.component;

import java.util.ArrayList;
import java.util.List;

public class PieChart extends Component {

    private int height;

    private List<DataPoint> points = new ArrayList<>();

    public PieChart() {}

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
