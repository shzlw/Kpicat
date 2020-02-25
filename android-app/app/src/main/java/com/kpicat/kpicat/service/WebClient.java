package com.kpicat.kpicat.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebClient {

    private URL url;
    private HttpURLConnection conn;

    int TIMEOUT = 10000;


    public String getWS(String urlString) {
        String rt = null;
        try {
            url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(TIMEOUT);

            int responseCode = conn.getResponseCode();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            rt = null;
        }  finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return rt;
    }

    public String postWS(String urlString, String data) {

        try {
            url = new URL(urlString);
            conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(TIMEOUT);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");

            conn.setDoOutput(true);
            DataOutputStream out = new DataOutputStream(conn.getOutputStream());
            out.writeBytes(data);
            out.flush();
            out.close();

            int responseCode = conn.getResponseCode();

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        return null;
    }
}
