package com.kpicat.sdk.java.model.component;

public class HorizontalBoxBuilder {

    private String backgroundColor;

    private Text leftText;

    private Text middleText;

    private Text rightText;

    public HorizontalBoxBuilder() {}

    public HorizontalBoxBuilder setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public HorizontalBoxBuilder setLeftText(Text leftText) {
        this.leftText = leftText;
        return this;
    }

    public HorizontalBoxBuilder setMiddleText(Text middleText) {
        this.middleText = middleText;
        return this;
    }

    public HorizontalBoxBuilder setRightText(Text rightText) {
        this.rightText = rightText;
        return this;
    }

    public HorizontalBox create() {
        return new HorizontalBox(backgroundColor, leftText, middleText, rightText);
    }
}
