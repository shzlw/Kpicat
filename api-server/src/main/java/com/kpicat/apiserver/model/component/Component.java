package com.kpicat.apiserver.model.component;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = VerticalBox.class, name = "verticalBox"),
        @JsonSubTypes.Type(value = HorizontalBox.class, name = "horizontalBox"),
        @JsonSubTypes.Type(value = PieChart.class, name = "pieChart"),
        @JsonSubTypes.Type(value = LineChart.class, name = "lineChart"),
        @JsonSubTypes.Type(value = BarChart.class, name = "barChart")
})
public abstract class Component {

    private String backgroundColor;

    public Component() {}

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
