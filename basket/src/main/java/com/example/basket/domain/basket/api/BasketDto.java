package com.example.basket.domain.basket.api;

import com.example.basket.domain.basket.api.basketitem.BasketItemDto;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class BasketDto {
    private long basketId;
    private double totalAmount;
    private int status;
    private long userId;
    private  List<BasketItemDto> basketItemList;

}
