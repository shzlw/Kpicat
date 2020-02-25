package com.kpicat.sdk.java.model.component;

public class RootResponse {
    
    private int responseCode;

    private String responseBody;
    
    public RootResponse(int responseCode, String responseBody) {
        this.responseCode = responseCode;
        this.responseBody = responseBody;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseBody() {
        return responseBody;
    }
}
