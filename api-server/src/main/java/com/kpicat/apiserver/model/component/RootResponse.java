package com.kpicat.apiserver.model.component;

public class RootResponse {


    private String message;

    private long limitRemaining = -1;

    public RootResponse() {}

    public RootResponse(String message) {
        this.message = message;
    }

    public RootResponse(String message, long limitRemaining) {
        this.message = message;
        this.limitRemaining = limitRemaining;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getLimitRemaining() {
        return limitRemaining;
    }

    public void setLimitRemaining(long limitRemaining) {
        this.limitRemaining = limitRemaining;
    }
}
