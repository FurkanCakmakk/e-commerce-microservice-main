package com.example.basket.domain.basket.api.basketitem;

import com.example.basket.domain.basket.impl.basketitem.BasketItem;

public interface BasketItemService {
    BasketItem findBasketItemByBasketIdAndProductId(long basketItemId , long productId);

    BasketItem save(BasketItem basketItem);

    void delete(long basketItemId);

}
