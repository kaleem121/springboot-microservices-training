package com.techTalkEra.orderservice.dto;

public class OrderResponse {

	private Long id;
	
	private String customerName;
	
	private String productCode;
	
	private Integer quantity;

	public OrderResponse(Long id, String customerName, String productCode, Integer quantity) {
		this.id = id;
		this.customerName = customerName;
		this.productCode = productCode;
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
