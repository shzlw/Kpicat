package com.kpicat.apiserver.model.component;

public class HorizontalBox extends Component {

    private Text leftText;

    private Text middleText;

    private Text rightText;

    public HorizontalBox() {}

    public Text getLeftText() {
        return leftText;
    }

    public void setLeftText(Text leftText) {
        this.leftText = leftText;
    }

    public Text getMiddleText() {
        return middleText;
    }

    public void setMiddleText(Text middleText) {
        this.middleText = middleText;
    }

    public Text getRightText() {
        return rightText;
    }

    public void setRightText(Text rightText) {
        this.rightText = rightText;
    }
}
