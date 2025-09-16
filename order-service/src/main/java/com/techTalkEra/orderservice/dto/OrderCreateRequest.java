package com.techTalkEra.orderservice.dto;

import jakarta.validation.constraints.*;

public class OrderCreateRequest {
	
	@NotBlank
	private String customerName;
    
	@NotBlank
	private String productCode;
	
	@Positive
	@Min(1)
	private Integer quantity;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	
	

}
