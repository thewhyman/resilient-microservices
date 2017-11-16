package com.smarttrader.marketdata.services;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface StockRepository extends CrudRepository<Stock, String> {

    Stock findByTickerIgnoreCase(@Param("ticker") String ticker);

    /**
     * Example of a Custom Query
     * @return
     */
    @Query("select s from Stock s")
    List<Stock> findAll();
}


