package com.kpicat.webserver.model;

public class Membership {

    private String name;

    private int user;

    private int api;

    private int fee;

    public Membership(String name, int user, int api, int fee) {
        this.name = name;
        this.user = user;
        this.api = api;
        this.fee = fee;
    }

    public String getName() {
        return name;
    }

    public int getUser() {
        return user;
    }

    public int getApi() {
        return api;
    }

    public int getFee() {
        return fee;
    }
}
