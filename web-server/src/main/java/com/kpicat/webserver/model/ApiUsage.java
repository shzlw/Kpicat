package com.kpicat.webserver.model;

import java.util.Date;

public class ApiUsage {

    private Date currDay;
    
    private long count;
    
    public ApiUsage() {}

    public Date getCurrDay() {
        return currDay;
    }

    public void setCurrDay(Date currDay) {
        this.currDay = currDay;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
