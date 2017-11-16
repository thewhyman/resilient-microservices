package com.smarttrader.marketdata.extensions;

import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.stereotype.Component;

@Component
public class DestroyEndpoint extends AbstractEndpoint<String> {

	public DestroyEndpoint() {
		super("destroy");
	}

	@Override
	public String invoke() {
        System.out.println("exit...");
		System.exit(0);
	    return "frightful";
	}

}
