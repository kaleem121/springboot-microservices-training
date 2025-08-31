package com.techTalkEra.helloservice;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductService {

    private final Map<Long, Product> store = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Product create(Product p) {
        long id = seq.getAndIncrement();
        p.setId(id);
        store.put(id, p);
        return p;
    }

    public List<Product> findAll() {
        return new ArrayList<>(store.values());
    }

    public Product findById(Long id) {
        Product p = store.get(id);
        if (p == null) throw new NotFoundException("Product " + id + " not found");
        return p;
    }

    public Product update(Long id, Product updated) {
        Product existing = store.get(id);
        if (existing == null) throw new NotFoundException("Product " + id + " not found");
        existing.setName(updated.getName());
        existing.setPrice(updated.getPrice());
        existing.setStock(updated.getStock());
        store.put(id, existing);
        return existing;
    }

    public void delete(Long id) {
        if (store.remove(id) == null) throw new NotFoundException("Product " + id + " not found");
    }
}

