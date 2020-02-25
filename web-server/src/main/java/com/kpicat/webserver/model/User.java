package com.kpicat.webserver.model;

import com.kpicat.webserver.service.Constants;

public class User {
    
    private String userId;

    private String email;

    /**
     * 8 - 20
     */
    private String username;

    /**
     * 8 - 20
     */
    private String password;

    /**
     * 8 - 20
     */
    private String corpName;

    private String corpRole;

    private String sysRole;

    /**
     * Generated
     */
    private String apiKey;

    /**
     * Generated
     */
    private String accountKey;

    /**
     * Generated
     */
    private String mobileKey;

    /**
     * Generated
     */
    private String sessionKey;

    private String membership;

    private String stripe;
    
    private String resetPassCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User() {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorpName() {
        return corpName;
    }

    public void setCorpName(String corpName) {
        this.corpName = corpName;
    }

    public String getCorpRole() {
        return corpRole;
    }

    public void setCorpRole(String corpRole) {
        this.corpRole = corpRole;
    }

    public String getSysRole() {
        return sysRole;
    }

    public void setSysRole(String sysRole) {
        this.sysRole = sysRole;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getMobileKey() {
        return mobileKey;
    }

    public void setMobileKey(String mobileKey) {
        this.mobileKey = mobileKey;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getResetPassCode() {
        return resetPassCode;
    }

    public void setResetPassCode(String resetPassCode) {
        this.resetPassCode = resetPassCode;
    }
}
