package com.kpicat.sdk.java.model.component;

public class HorizontalBox extends Component {
    
    private static final String HORIZONTALBOX = "horizontalBox";

    private Text leftText;

    private Text middleText;

    private Text rightText;

    public HorizontalBox(String backgroundColor, Text leftText, Text middleText, Text rightText) {
        super(HORIZONTALBOX);
        setBackgroundColor(backgroundColor);
        this.leftText = leftText;
        this.middleText = middleText;
        this.rightText = rightText;
    }

    public Text getLeftText() {
        return leftText;
    }

    public Text getMiddleText() {
        return middleText;
    }

    public Text getRightText() {
        return rightText;
    }
}
