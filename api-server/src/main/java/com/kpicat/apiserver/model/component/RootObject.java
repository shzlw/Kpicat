package com.kpicat.apiserver.model.component;

import java.io.Serializable;

public class RootObject implements Serializable {

    private String componentId;

    private String apiKey;

    private long timestamp;

    private Component data;

    public RootObject() {}

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Component getData() {
        return data;
    }

    public void setData(Component data) {
        this.data = data;
    }
}
