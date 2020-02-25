package com.kpicat.sdk.java.model.component;

public class VerticalBox extends Component {
    
    private static final String VERTICALBOX = "verticalBox";

    private Text topText;

    private Text bottomText;

    public VerticalBox(String backgroundColor, Text topText, Text bottomText) {
        super(VERTICALBOX);
        setBackgroundColor(backgroundColor);
        this.topText = topText;
        this.bottomText = bottomText;
    }

    public Text getTopText() {
        return topText;
    }

    public Text getBottomText() {
        return bottomText;
    }
}
