package com.kpicat.sdk.java.model.component;

public class ComponentObjectBuilder {

    private String componentId;

    private String apiKey;

    private long timestamp;

    private Component data;

    public ComponentObjectBuilder() {}

    public ComponentObjectBuilder setComponentId(String componentId) {
        this.componentId = componentId;
        return this;
    }

    public ComponentObjectBuilder setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public ComponentObjectBuilder setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public ComponentObjectBuilder setData(Component data) {
        this.data = data;
        return this;
    }
    
    public ComponentObject create() {
        return new ComponentObject(componentId, apiKey, timestamp, data);
    }
}
