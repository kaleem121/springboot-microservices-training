package com.techTalkEra.orderservice.dto;

import jakarta.validation.constraints.*;

public class OrderUpdateRequest {

	@NotBlank
	private String customerName;
	
	@Positive
	@Min(1)
	private Integer quattity;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getQuattity() {
		return quattity;
	}

	public void setQuattity(Integer quattity) {
		this.quattity = quattity;
	}
	
	
	
}
