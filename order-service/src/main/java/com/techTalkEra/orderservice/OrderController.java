package com.techTalkEra.orderservice;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techTalkEra.orderservice.dto.OrderCreateRequest;
import com.techTalkEra.orderservice.dto.OrderResponse;
import com.techTalkEra.orderservice.dto.OrderUpdateRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService service;

	public OrderController(OrderService service) {
		this.service = service;
	}
	@PostMapping
	public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreateRequest req) {
		
		var res= service.create(req);
		
	    return ResponseEntity.created(URI.create("/orders/"+res.getId())).body(res);
		
	}	
	@GetMapping("/{id}")
	public OrderResponse get(@PathVariable Long id ){
		OrderResponse res = service.get(id);
		return res;
	}
     
	@GetMapping
	public List<OrderResponse> list(){
		return service.list();
	}
	
	@PutMapping("/{id}")
	public OrderResponse update(@PathVariable Long id, @Valid @RequestBody OrderUpdateRequest req) {
		OrderResponse updated = service.update(id, req);
		return updated;
		
	}
	@PostMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	
}
