package com.kpicat.kpicat.model;

public class VerticalBox extends RootComponent {

    private Text topText;

    private Text bottomText;

    public VerticalBox() {}

    public Text getTopText() {
        return topText;
    }

    public void setTopText(Text topText) {
        this.topText = topText;
    }

    public Text getBottomText() {
        return bottomText;
    }

    public void setBottomText(Text bottomText) {
        this.bottomText = bottomText;
    }
}
