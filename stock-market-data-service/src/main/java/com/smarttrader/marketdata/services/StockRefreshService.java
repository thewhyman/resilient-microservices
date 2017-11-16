package com.smarttrader.marketdata.services;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * This is only for making demo simple.
 * Use Spring Batch or other technologies.
 */

@Component
@EnableAsync
public class StockRefreshService {

    private StockRepository stockRepository;

    @Autowired
    public StockRefreshService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Async
    public String refreshStocks() throws InterruptedException {
        while(true) {
            System.out.println("Updating stock prices ........");
            Thread.sleep(5000);
            for (Stock stock : stockRepository.findAll()) {
                stock.setPrice(stock.getPrice() - 2 + RandomUtils.nextInt(3));
                stockRepository.save(stock);
            }
        }
    }
}