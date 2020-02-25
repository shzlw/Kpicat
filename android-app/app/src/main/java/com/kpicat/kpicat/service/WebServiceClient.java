package com.kpicat.kpicat.service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kpicat.kpicat.model.Configuration;
import com.kpicat.kpicat.model.Page;
import com.kpicat.kpicat.model.Row;
import com.kpicat.kpicat.model.User;

import java.util.ArrayList;
import java.util.List;

public class WebServiceClient {

    public static final String HOST_URL = "https://kpicat.com";

    public static String MOBILE_KEY = "";

    private WebClient webClient = new WebClient();

    private ObjectMapper objectMapper = new ObjectMapper();

    public WebServiceClient() {
        // Add this to handle the new properties added on the server side.
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

    public String login(String username, String password, String corpName) {
        String url = HOST_URL + "/auth/mobile/login";
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCorpName(corpName);
        String jsonData = "";
        try {
            jsonData = objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = webClient.postWS(url, jsonData);

        return result;
    }

    public String login(String mobileKey) {
        String url = HOST_URL + "/auth/mobile/login/mobile-key";
        User user = new User();
        user.setMobileKey(mobileKey);
        String jsonData = "";
        try {
            jsonData = objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
        }

        String result = webClient.postWS(url, jsonData);

        return result;
    }


    public Configuration fetchConfiguration(String mobileKey) {
        String url = HOST_URL + "/ws/mobile/configuration/one?mobileKey=" + mobileKey;
        String result = webClient.getWS(url);

        Configuration config = null;
        try {
            config = objectMapper.readValue(result, Configuration.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return config;
    }


    public List<Page> fetchPages(String mobileKey) {
        String url = HOST_URL + "/ws/mobile/page/all?mobileKey=" + mobileKey;
        String result = webClient.getWS(url);

        List<Page> pages = new ArrayList<Page>();
        try {
            pages = objectMapper.readValue(result, objectMapper.getTypeFactory().constructCollectionType(List.class, Page.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pages;
    }

    public List<Row> fetchRows(String pageId, String mobileKey) {
        String url = HOST_URL + "/ws/mobile/row/all?mobileKey=" + mobileKey + "&pageId=" + pageId;
        String result = webClient.getWS(url);

        List<Row> rows = new ArrayList<Row>();
        try {
            rows = objectMapper.readValue(result, objectMapper.getTypeFactory().constructCollectionType(List.class, Row.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rows;
    }


}
