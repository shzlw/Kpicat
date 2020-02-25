package com.kpicat.apiserver.service;


import com.fasterxml.jackson.databind.ObjectMapper;

import com.kpicat.apiserver.model.message.FcmMessage;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class FirebaseService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private static String FCM_KEY = "ENTER FCM KEY";

    private static String FCM_URL = "https://fcm.googleapis.com/fcm/send";

    @Autowired
    ObjectMapper objectMapper;

    public void sendMessage(String topic, String msgTitle, String msgBody) {

        log.info("[FirebaseService] start");

        FcmMessage fcmMessage = new FcmMessage(topic, msgTitle, msgBody);
        String msg = "";
        try {
            msg = objectMapper.writeValueAsString(fcmMessage);
            send(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("[FirebaseService] message: {}", msg);
    }

    private void send(String message) throws ClientProtocolException, IOException {

        int timeout = 5;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout * 1000)
                .setConnectionRequestTimeout(timeout * 1000)
                .setSocketTimeout(timeout * 1000).build();

        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        HttpPost httpPost = new HttpPost(FCM_URL);

        httpPost.addHeader(HttpHeaders.CONTENT_TYPE, "application/json");
        httpPost.addHeader(HttpHeaders.AUTHORIZATION, "key=" + FCM_KEY);

        StringEntity params = new StringEntity(message);
        httpPost.setEntity(params);

        CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity entity = httpResponse.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        log.info("[FirebaseService] response: {}", responseString);

        httpClient.close();
    }


}