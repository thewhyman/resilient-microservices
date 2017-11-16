package com.smarttrader.marketdata.services;

import com.smarttrader.marketdata.services.Stock;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

/**
 * Since 'ticker' is an Id field, this is turned off by default.
 * Need to expose Ticker name in response json output.
 */

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {
    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Stock.class);
    }
}