package com.techTalkEra.helloservice;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.techTalkEra.helloservice.dto.ProductCreateRequest;
import com.techTalkEra.helloservice.dto.ProductResponse;
import com.techTalkEra.helloservice.dto.ProductUpdateRequest;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/products")
@Validated
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductCreateRequest req) {
        Product saved = service.create(ProductMapper.toEntity(req));
        return ResponseEntity.created(URI.create("/products/" + saved.getId())).body(ProductMapper.toResponse(saved));
    }

    @GetMapping
    public List<ProductResponse> all() {
        return ProductMapper.toResponseList(service.findAll());
    }

    @GetMapping("/{id}")
    public ProductResponse one(@PathVariable Long id) {
        return ProductMapper.toResponse(service.findById(id));
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductUpdateRequest req) {
      Product updated = service.update(id, req);
      return ProductMapper.toResponse(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
