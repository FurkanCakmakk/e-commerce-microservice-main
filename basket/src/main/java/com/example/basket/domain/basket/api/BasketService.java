package com.example.basket.domain.basket.api;

public interface BasketService {
    BasketDto addProductToBasket(BasketDto basketDto);

    BasketDto getBasketByUserId(String userId);

    void removeProductFromBasket(String userId , String basketItemId);

}
