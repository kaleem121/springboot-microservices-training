package com.techTalkEra.productservice;

import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.MeterRegistry;

@Service
public class ProductMetrics {
	
	private final MeterRegistry registry;
	  public ProductMetrics(MeterRegistry registry) { this.registry = registry; }

	  public void countCreated(String name) {
	    registry.counter("products.created", "name", name).increment();
	  }

}
