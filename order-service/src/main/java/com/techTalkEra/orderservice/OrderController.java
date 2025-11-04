package com.techTalkEra.orderservice;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techTalkEra.common.dto.OrderEvent;
import com.techTalkEra.orderservice.dto.OrderCreateRequest;
import com.techTalkEra.orderservice.dto.OrderRequest;
import com.techTalkEra.orderservice.dto.OrderResponse;
import com.techTalkEra.orderservice.dto.OrderUpdateRequest;
import com.techTalkEra.orderservice.dto.ProductResponse;

import feign.FeignException;
import feign.FeignException.FeignClientException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderService service;
	
	@Autowired
	private final OrderKafkaProducer producer;
	
	@Autowired
	private ProductClient productClient;

	public OrderController(OrderService service,OrderKafkaProducer producer) {
		this.service = service;
		this.producer =producer;
	}
	@PostMapping
	public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderCreateRequest req) {
		
		var res= service.create(req);
		
	    return ResponseEntity.created(URI.create("/orders/"+res.getId())).body(res);
		
	}	
	/*
	 * @GetMapping("/{id}") public OrderResponse get(@PathVariable Long id ){
	 * OrderResponse res = service.get(id); return res; }
	 */
     
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
	@GetMapping("/{OrderId}")
	public ResponseEntity<String> getOrderInfo(@PathVariable Long OrderId) {
		
		ProductResponse product = productClient.getProduct(26l);
		return ResponseEntity.ok("Order " + OrderId + " For product" + product.getName());

	}
	
	// New endpoint to create order and publish event
    @PostMapping("/publish")
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest req) {
        // 1) Optional: validate product exists by calling Product Service
        try {
        	System.out.println("Order ID from request: " + req.getOrderId());
            ProductResponse product = productClient.getProduct(req.getOrderId());
            // optional: check product availability etc.
        } catch (Exception e) {
            // if product service says product not found or is down, handle accordingly
            return ResponseEntity.status(503).body("Unable to validate product: " + e.getMessage());
        }

        // 2) Normally: save order to DB here. For demo we skip DB and publish event.
        OrderEvent event = new OrderEvent(req.getOrderId(), req.getProductId(), req.getQuantity());

        // 3) Publish event to Kafka (async)
        producer.sendOrderEvent(event);

        // 4) Return success immediately (fire-and-forget)
        return ResponseEntity.ok("Order placed and event sent for orderId=" + req.getOrderId());
    }
}
