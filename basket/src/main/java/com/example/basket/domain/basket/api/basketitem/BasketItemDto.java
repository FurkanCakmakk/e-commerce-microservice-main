package com.example.basket.domain.basket.api.basketitem;

import com.example.basket.domain.basket.api.product.ProductDto;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class BasketItemDto {
    private long basketItemId;
    private double basketItemAmount;
    private int count;
    private ProductDto product;
}
