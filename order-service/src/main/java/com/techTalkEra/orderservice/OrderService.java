package com.techTalkEra.orderservice;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techTalkEra.orderservice.dto.OrderCreateRequest;
import com.techTalkEra.orderservice.dto.OrderResponse;
import com.techTalkEra.orderservice.dto.OrderUpdateRequest;

@Service
public class OrderService {
	

	  private final OrderRepository repo;

	  public OrderService(OrderRepository repo) { this.repo = repo; }

	  @Transactional
	  public OrderResponse create(OrderCreateRequest req){
	    var e = OrderMapper.toEntity(req);
	    e = repo.save(e);
	    return OrderMapper.toResponse(e);
	  }

	  public OrderResponse get(Long id){
	    var e = repo.findById(id).orElseThrow(() -> new NotFoundException("Order %d not found".formatted(id)));
	    return OrderMapper.toResponse(e);
	  }

	  public java.util.List<OrderResponse> list(){
	    return repo.findAll().stream().map(OrderMapper::toResponse).toList();
	  }

	  @Transactional
	  public OrderResponse update(Long id, OrderUpdateRequest req){
	    var e = repo.findById(id).orElseThrow(() -> new NotFoundException("Order %d not found".formatted(id)));
	    OrderMapper.applyUpdate(e, req);
	    return OrderMapper.toResponse(e);
	  }

	  @Transactional
	  public void delete(Long id){
	    if (!repo.existsById(id)) throw new NotFoundException("Order %d not found".formatted(id));
	    repo.deleteById(id);
	  }
	  
	
}
