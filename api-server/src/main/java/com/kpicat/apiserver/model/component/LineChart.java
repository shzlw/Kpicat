package com.kpicat.apiserver.model.component;

import java.util.ArrayList;
import java.util.List;

public class LineChart extends Component {

    private boolean cubic;

    private boolean xNumber;

    private String fillColor;

    private int height;

    private List<DataPoint> points = new ArrayList<>();

    public LineChart() {}

    public boolean isCubic() {
        return cubic;
    }

    public void setCubic(boolean cubic) {
        this.cubic = cubic;
    }

    public boolean isxNumber() {
        return xNumber;
    }

    public void setxNumber(boolean xNumber) {
        this.xNumber = xNumber;
    }

    public String getFillColor() {
        return fillColor;
    }

    public void setFillColor(String fillColor) {
        this.fillColor = fillColor;
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
