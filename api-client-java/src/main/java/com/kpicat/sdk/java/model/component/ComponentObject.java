package com.kpicat.sdk.java.model.component;

public class ComponentObject {

    private String componentId;

    private String apiKey;

    private long timestamp;

    private Component data;
    
    public ComponentObject(String componentId, String apiKey, long timestamp, Component data) {
        this.componentId = componentId;
        this.apiKey = apiKey;
        this.timestamp = timestamp;
        this.data = data;
    }

    public String getComponentId() {
        return componentId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Component getData() {
        return data;
    }
}
