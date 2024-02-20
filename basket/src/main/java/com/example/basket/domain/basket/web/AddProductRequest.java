package com.example.basket.domain.basket.web;

import com.example.basket.domain.basket.api.BasketDto;
import com.example.basket.domain.basket.api.basketitem.BasketItemDto;
import com.example.basket.domain.basket.api.product.ProductDto;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class AddProductRequest {
    private long userId;
    private long productId;
    private int count;

    public BasketDto toDto() {
        List<BasketItemDto> dtoList = new ArrayList<>();
        BasketItemDto dto = BasketItemDto.builder().product(new ProductDto(productId)).build();
        dto.setCount(count);
        dtoList.add(dto);
        return BasketDto.builder()
                .basketItemList(dtoList)
                .userId(userId)
                .build();
    }
}
