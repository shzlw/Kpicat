package com.kpicat.apiserver.service;

import com.kpicat.apiserver.model.Membership;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Constants {

    public static final String BETA = "Beta";
    public static final String BASIC = "Basic";
    public static final String PREMIUM = "Premium";

    public static final Map<String, Membership> MEMBERSHIP_MAP;
    static {
        Map<String, Membership> m  = new HashMap<>();
        m.put(BETA, new Membership(BETA, 20, 50000,0));
        m.put(BASIC, new Membership(BASIC, 5, 10000, 0));
        m.put(PREMIUM, new Membership(PREMIUM, 50, 100000, 50));
        MEMBERSHIP_MAP = Collections.unmodifiableMap(m);
    }

}
