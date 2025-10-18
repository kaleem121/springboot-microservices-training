package com.techTalkEra.orderservice;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String message) {

		super(message);
	}

}
