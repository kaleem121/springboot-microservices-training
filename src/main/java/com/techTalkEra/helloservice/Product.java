package com.techTalkEra.helloservice;

import java.math.BigDecimal;

import jakarta.validation.constraints.*;

public class Product {
    private Long id;

    @NotBlank
    private String name;

    @NotNull @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal price;

    @NotNull @Min(0)
    private Integer stock;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
}
