package com.kpicat.sdk.java.model.component;

public class VerticalBoxBuilder {

    private String backgroundColor;
    
    private Text topText;

    private Text bottomText;
    
    public VerticalBoxBuilder() {}

    public VerticalBoxBuilder setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public VerticalBoxBuilder setTopText(Text topText) {
        this.topText = topText;
        return this;
    }

    public VerticalBoxBuilder setBottomText(Text bottomText) {
        this.bottomText = bottomText;
        return this;
    }

    public VerticalBox create() {
        return new VerticalBox(backgroundColor, topText, bottomText);
    }
}
