package com.example.basket.domain.basket.api.product;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProductDto {
    private long productId;
    private String name;
    private int stock;
    private double price;

    public ProductDto(long productId){
        this.productId = productId;
    }
}
