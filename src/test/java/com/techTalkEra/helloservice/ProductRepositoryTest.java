package com.techTalkEra.helloservice;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // starts JPA layer with H2 in-memory DB (fast, no Docker needed)
public class ProductRepositoryTest {
	
	 @Autowired ProductRepository repo;

	  @Test
	  void save_and_find() {
	    // 1) Create entity
	    Product p = new Product();
	    p.setName("Pencil");
	    p.setPrice(new BigDecimal("5.50"));
	    p.setStock(10);

	    // 2) Save
	    Product saved = repo.save(p);
	    assertThat(saved.getId()).isNotNull(); // ID assigned

	    // 3) Read back
	    var found = repo.findById(saved.getId());
	    assertThat(found).isPresent();
	    assertThat(found.get().getName()).isEqualTo("Pencil");
	  }
	

}
