package com.example.stock.domain.product.web;

import com.example.stock.domain.category.api.CategoryDto;
import com.example.stock.domain.product.api.ProductDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
    private String name;
    private int stock;
    private double price;
    private String categoryId;

    public ProductDto toDto(){
        return ProductDto.builder()
                .name(this.name)
                .stock(this.stock)
                .price(this.price)
                .categoryDto(CategoryDto.builder()
                        .categoryId(Long.valueOf(this.categoryId))
                        .build())
                .build();
    }
}
