package com.techTalkEra.helloservice;

import com.techTalkEra.helloservice.dto.*;
import java.util.List;
public class ProductMapper {

	public static Product toEntity(ProductCreateRequest r) {
	    Product p = new Product();
	    p.setName(r.getName());
	    p.setPrice(r.getPrice());
	    p.setStock(r.getStock());
	    return p;
	  }

	  public static void copy(ProductUpdateRequest r, Product e) {
	    e.setName(r.getName());
	    e.setPrice(r.getPrice());
	    e.setStock(r.getStock());
	  }

	  public static ProductResponse toResponse(Product e) {
	    return new ProductResponse(e.getId(), e.getName(), e.getPrice(), e.getStock());
	  }

	  public static List<ProductResponse> toResponseList(List<Product> list) {
	    return list.stream().map(ProductMapper::toResponse).toList();
	  }
}
