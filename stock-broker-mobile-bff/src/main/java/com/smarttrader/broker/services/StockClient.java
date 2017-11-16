package com.smarttrader.broker.services;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("stock-market-data-service")
public interface StockClient {

    @RequestMapping(method = RequestMethod.GET, value = "/stocks")
    Resources<Stock> getAllStocks();

    @RequestMapping(method = RequestMethod.GET, value = "/stocks/{ticker}")
    Stock getStock(@PathVariable("ticker") String ticker);
}