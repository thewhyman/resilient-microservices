package com.smarttrader.marketdata;

import com.smarttrader.marketdata.services.Stock;
import com.smarttrader.marketdata.services.StockRefreshService;
import com.smarttrader.marketdata.services.StockRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class StockMarketDataServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StockMarketDataServiceApplication.class, args);
    }

    /**
     * Populate data.
     *
     * @param repository
     * @return
     */
    @Bean
    public InitializingBean seedDatabase(StockRepository repository) {
        return () -> {
            repository.save(new Stock("AAPL", "Apple Inc", 147.45, 10000000));
            repository.save(new Stock("SCHW", "Charles Schwab", 43.56, 20000000));
            repository.save(new Stock("GOOGL", "Alphabet", 1007.45, 300000000));
            repository.save(new Stock("AMZN", "Amazon", 1133.39, 40000000));
            repository.save(new Stock("SSNLF", "Samsung", 2400.45, 500000000));
        };
    }

    /**
     * Run sample query to see whether data is loaded properly
     *
     * @param repository
     * @return
     */
    @Bean
    public CommandLineRunner exampleQuery(StockRepository repository) {
        return args -> repository.findByTickerIgnoreCase("AAPL");
    }


    /**
     * Invoke call to run background task. This is only for making demo simple.
     * Use Spring Batch or other technologies.
     */
    @Bean
    public CommandLineRunner updateStocks(StockRefreshService stockRefreshService) throws InterruptedException {
        return args -> {
            stockRefreshService.refreshStocks();
        };
    }
}
