package com.techTalkEra.helloservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techTalkEra.productservice.Product;
import com.techTalkEra.productservice.ProductController;
import com.techTalkEra.productservice.ProductService;
import com.techTalkEra.productservice.dto.ProductCreateRequest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class) // Start only web layer (controller + MVC)
public class ProductControllerTest {


	  @Autowired MockMvc mvc;           // lets us call endpoints without real server
	  @Autowired ObjectMapper om;       // converts Java objects <-> JSON

	  @MockBean ProductService service; // controller depends on service, so we mock it

	  @Test
	  void create_returns201_and_body() throws Exception {
	    // 1) Fake what the service will return after save
	    Product saved = new Product();
	    saved.setId(1L);
	    saved.setName("Pen");
	    saved.setPrice(new BigDecimal("10.00"));
	    saved.setStock(5);
	    when(service.create(any(Product.class))).thenReturn(saved);

	    // 2) Build the request body like you do in Postman
	    ProductCreateRequest req = new ProductCreateRequest();
	    req.setName("Pen");
	    req.setPrice(new BigDecimal("10.00"));
	    req.setStock(5);

	    // 3) Perform POST /products and assert the response
	    mvc.perform(post("/products")
	        .contentType(MediaType.APPLICATION_JSON)
	        .content(om.writeValueAsString(req)))
	      .andExpect(status().isCreated())
	      .andExpect(header().string("Location", "/products/1"))
	      .andExpect(jsonPath("$.id").value(1))
	      .andExpect(jsonPath("$.name").value("Pen"));
	  }
	
}
