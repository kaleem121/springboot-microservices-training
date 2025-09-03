package com.techTalkEra.helloservice;

import org.springframework.stereotype.Service;

import com.techTalkEra.helloservice.dto.ProductUpdateRequest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
	
	private final ProductRepository repo;
	
	public ProductService(ProductRepository repo)
	{
		this.repo=repo;
	}

    //private final Map<Long, Product> store = new ConcurrentHashMap<>();

    public Product create(Product p) {
        return repo.save(p);
    }

    public List<Product> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    public Product findById(Long id) {
      return repo.findById(id).orElseThrow(() -> new NotFoundException("Product " + id + " not found"));
    }

    public Product update(Long id, ProductUpdateRequest req) {
    	return repo.findById(id).map(existing -> {
    	    ProductMapper.copy(req, existing);
    	    return repo.save(existing);
    	  }).orElseThrow(() -> new NotFoundException("Product " + id + " not found"));
      }

      public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Product " + id + " not found");
        repo.deleteById(id);
      }
   
}

