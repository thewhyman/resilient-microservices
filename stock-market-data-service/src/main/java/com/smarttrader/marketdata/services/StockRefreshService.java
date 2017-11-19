package com.smarttrader.marketdata.services;

import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

/**
 * This is only for making demo simple.
 * Use Spring Batch or other technologies.
 */

@Component
@EnableAsync
public class StockRefreshService {

    private static final Logger logger = LoggerFactory.getLogger(StockRefreshService.class);

    private StockRepository stockRepository;

    @Autowired
    public StockRefreshService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Async
    public String refreshStocks() throws InterruptedException {
        while(true) {
            logger.debug("Updating stock prices ........");
            Thread.sleep(5000);
            for (Stock stock : stockRepository.findAll()) {
                stock.setPrice(stock.getPrice() - 2 + RandomUtils.nextInt(3));
                stockRepository.save(stock);
            }
        }
    }
}