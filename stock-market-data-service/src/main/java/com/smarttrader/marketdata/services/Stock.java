package com.smarttrader.marketdata.services;

import javax.persistence.Entity;
import javax.persistence.Id;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.Date;

@Entity
public class Stock {

    @Id
    private String ticker;
    private String name;
    private Double price;
    private int volume;


    @LastModifiedDate
    private Date lastUpdatedTime;

    private Stock() {
    }

    public Stock(String ticker, String name, Double price, int volume) {
        this.ticker = ticker;
        this.name = name;
        this.price = price;
        this.volume = volume;
        this.lastUpdatedTime = new Date();
    }

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

    public int getVolume() {
        return volume;
    }

    public void setPrice(Double price) {
        this.price = price;
        this.lastUpdatedTime = new Date();
    }
}
