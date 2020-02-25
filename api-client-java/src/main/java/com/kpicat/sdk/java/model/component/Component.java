package com.kpicat.sdk.java.model.component;

public abstract class Component {

    private String backgroundColor;

    private String type;

    protected Component(String type) {
        this.type = type;
    }

    protected String getType() {
        return type;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
