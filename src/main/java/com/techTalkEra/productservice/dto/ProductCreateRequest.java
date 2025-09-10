package com.techTalkEra.helloservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductCreateRequest {
	
	 @NotBlank(message = "name is required")
	  private String name;

	  @NotNull(message = "price is required")
	  @DecimalMin(value = "0.0", inclusive = true, message = "price must be >= 0")
	  @Digits(integer = 10, fraction = 2, message = "price format invalid")
	  private BigDecimal price;

	  @NotNull(message = "stock is required")
	  @Min(value = 0, message = "stock must be >= 0")
	  private Integer stock;

	  // getters/setters
	  public String getName() { return name; }
	  public void setName(String name) { this.name = name; }
	  public BigDecimal getPrice() { return price; }
	  public void setPrice(BigDecimal price) { this.price = price; }
	  public Integer getStock() { return stock; }
	  public void setStock(Integer stock) { this.stock = stock; }
	

}
