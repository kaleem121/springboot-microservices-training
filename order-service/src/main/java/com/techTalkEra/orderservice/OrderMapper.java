package com.techTalkEra.orderservice;

import com.techTalkEra.orderservice.dto.OrderCreateRequest;
import com.techTalkEra.orderservice.dto.OrderResponse;
import com.techTalkEra.orderservice.dto.OrderUpdateRequest;

public class OrderMapper {

	public static OrderEntity toEntity(OrderCreateRequest r) {
		OrderEntity e = new OrderEntity();
		e.setCustomerName(r.getCustomerName());
		e.setProductCode(r.getProductCode());
		e.setQuantity(r.getQuantity());
		return e;
	}
	
	public static void  applyUpdate(OrderEntity e, OrderUpdateRequest r) {
		e.setCustomerName(r.getCustomerName());
		e.setQuantity(r.getQuattity());
	}
	
	public static OrderResponse toResponse(OrderEntity e) {
		return new OrderResponse(e.getId(),e.getCustomerName(),e.getProductCode(),e.getQuantity());
	}
}
