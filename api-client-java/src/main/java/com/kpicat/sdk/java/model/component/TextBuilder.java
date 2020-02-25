package com.kpicat.sdk.java.model.component;

public class TextBuilder {

    private String value;

    private int size;

    private String color;
    
    public TextBuilder() {}

    public String getValue() {
        return value;
    }

    public TextBuilder setValue(String value) {
        this.value = value;
        return this;
    }

    public TextBuilder setSize(int size) {
        this.size = size;
        return this;
    }
    
    public TextBuilder setColor(String color) {
        this.color = color;
        return this;
    }
    
    public Text create() {
        return new Text(value, size, color);
    }
}
