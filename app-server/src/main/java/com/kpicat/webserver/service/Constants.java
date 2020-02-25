package com.kpicat.webserver.service;

import com.kpicat.webserver.model.Membership;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String SESSION_KEY = "kc_skey";
    public static final int COOKIE_TIMEOUT = 86400;
    public static final String ADMIN = "admin";
    public static final String VIEWER = "viewer";

    public static final String BETA = "Beta";
    public static final String BASIC = "Free";
    public static final String PREMIUM = "Premium";

    public static final String GOOD = "good";
    public static final String ERROR = "error";

    public static final Map<String, Membership> MEMBERSHIP_MAP;
    static {
        Map<String, Membership> m  = new HashMap<>();
        m.put(BETA, new Membership(BETA, 20, 50000,0));
        m.put(BASIC, new Membership(BASIC, 5, 10000, 0));
        m.put(PREMIUM, new Membership(PREMIUM, 50, 100000, 50));
        MEMBERSHIP_MAP = Collections.unmodifiableMap(m);
    }
}
