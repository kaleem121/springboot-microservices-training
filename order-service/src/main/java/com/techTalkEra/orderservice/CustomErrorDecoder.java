package com.techTalkEra.orderservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class CustomErrorDecoder implements ErrorDecoder {

	@Override
	public Exception decode(String methodKey, Response response) {
		
		// in CustomErrorDecoder
		final Logger log = LoggerFactory.getLogger(CustomErrorDecoder.class);
		log.error("CustomErrorDecoder invoked for methodKey={}, status={}", methodKey, response.status());

		
		switch(response.status()) {
		case 404:
			 // If Feign gets HTTP 404 from the other service
			return new ProductNotFoundException("Product not found");
		case 503:
			 // If the other service crashed or returned HTTP 503
			return new ProductServiceDownException("Product service is down!");
		default :
			//Any other error
			return new FeignGenericException("General feign error : "+response.status());
		
		
		}
	}

}
