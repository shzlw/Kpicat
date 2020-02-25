package com.kpicat.apiserver.model.component;

public class VerticalBox extends Component {

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
