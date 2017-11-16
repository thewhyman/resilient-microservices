package com.smarttrader.broker.services;

import java.util.Date;

public class Stock {

    private String ticker;
    private String name;
    private Double price;

    private Date lastUpdatedTime;

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Date getLastUpdatedTime() {
        return lastUpdatedTime;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }
}
