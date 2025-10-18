package com.techTalkEra.orderservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.techTalkEra.orderservice.dto.ProductResponse;

@FeignClient(name = "product-service")
public interface ProductClient {

	@GetMapping("/products/{id}")
	ProductResponse getProduct(@PathVariable Long id);
}
