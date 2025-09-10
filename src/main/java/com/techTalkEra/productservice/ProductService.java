package com.techTalkEra.helloservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.techTalkEra.helloservice.dto.ProductUpdateRequest;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);
	
	private final ProductRepository repo;
	 private final ProductMetrics metrics;
	
	public ProductService(ProductRepository repo,ProductMetrics metrics)
	{
		this.repo=repo;
		this.metrics=metrics;
	}

    //private final Map<Long, Product> store = new ConcurrentHashMap<>();

    public Product create(Product p) {
    	
    	log.info("Creating product: {}", p.getName());
        Product saved = repo.save(p);
        metrics.countCreated(saved.getName());
        return saved;
    }

    public List<Product> findAll() {
        return new ArrayList<>(repo.findAll());
    }

    public Product findById(Long id) {
    	log.debug("Fetching product id={}", id);
      return repo.findById(id).orElseThrow(() -> {
    	  log.warn("Product {} not found", id);
    	  return new NotFoundException("Product " + id + " not found");
    	  });
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

