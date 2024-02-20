package com.example.stock.domain.product.web;

import com.example.stock.domain.category.api.CategoryDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
    private Long productId;
    private String name;
    private int stock;
    private double price;
    private CategoryDto categoryDto;

}
