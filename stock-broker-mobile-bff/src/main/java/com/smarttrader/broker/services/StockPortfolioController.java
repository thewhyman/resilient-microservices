package com.smarttrader.broker.services;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@EnableCircuitBreaker
@EnableFeignClients
@EnableDiscoveryClient
//Hateoas support for Feign - Spring Boot Data REST client
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
public class StockPortfolioController {

    /**
     * Simple in-memory cache of stocks.
     * Since this is an in-memroy cache, each app instace will maintain its own cache.
     * This is only to simplify demo.
     * In real systems use external caching solution.
     */
    private static final Map<String, Stock> stocksCache = new HashMap<>();

    private final StockClient stockClient;

    @Autowired
    public StockPortfolioController(StockClient stockClient) {
        this.stockClient = stockClient;
    }

    @HystrixCommand(fallbackMethod = "getCachedQuote")
    @RequestMapping("/portfolio/{ticker}")
    public Stock getQuote(@PathVariable String ticker) {
        return cacheStock(this.stockClient.getStock(ticker));
    }

    public Stock getCachedQuote(@PathVariable("ticker") String ticker) {
        return stocksCache.get(ticker);
    }


    @HystrixCommand(fallbackMethod = "getAllCachedQuotes")
    @RequestMapping("/portfolio")
    public Collection<Stock> getAllQuotes() {
        Collection<Stock> stocks = this.stockClient.getAllStocks().getContent();
        for (Stock stock : stocks) {
            cacheStock(stock);
        }
        return stocks;
    }

    public Collection<Stock> getAllCachedQuotes() {
        return stocksCache.values().stream().collect(Collectors.toList());
    }

    /**
     * Simple in-memory cache.
     * Use exteranl caching solutions such as Redis
     *
     * @param stock
     * @return
     */
    private Stock cacheStock(Stock stock) {
        stocksCache.put(stock.getTicker(), stock);
        return stock;
    }

}
