package com.techTalkEra.productservice.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class ProductUpdateRequest {

	 @NotBlank(message = "name is required")
	  private String name;

	  @NotNull @DecimalMin(value = "0.0", inclusive = true)
	  @Digits(integer = 10, fraction = 2)
	  private BigDecimal price;

	  @NotNull @Min(0)
	  private Integer stock;

	  // getters/setters
	  public String getName() { return name; }
	  public void setName(String name) { this.name = name; }
	  public BigDecimal getPrice() { return price; }
	  public void setPrice(BigDecimal price) { this.price = price; }
	  public Integer getStock() { return stock; }
	  public void setStock(Integer stock) { this.stock = stock; }
	
}
