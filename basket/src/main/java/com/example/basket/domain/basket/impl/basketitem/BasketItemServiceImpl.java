package com.example.basket.domain.basket.impl.basketitem;

import com.example.basket.domain.basket.api.basketitem.BasketItemDto;
import com.example.basket.domain.basket.api.basketitem.BasketItemService;
import com.example.basket.domain.basket.api.product.ProductDto;
import com.example.basket.domain.basket.impl.Basket;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BasketItemServiceImpl implements BasketItemService {

    private final BasketItemRepository repository;
    private final RestTemplate restTemplate;
    private final String PRODUCT_SERVICE = "http://localhost:8090/stock/products/";

    public BasketItemServiceImpl(BasketItemRepository repository, RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    @Override
    public BasketItem findBasketItemByBasketIdAndProductId(long basketItemId, long productId) {
        BasketItem basketItem = repository.findBasketItemByBasket_BasketIdAndProductId(basketItemId , productId);
        return  basketItem;
    }

    @Override
    public BasketItem save(BasketItem basketItem) {
        return repository.save(basketItem);
    }


    @Override
    public void delete(long basketItemId) {
        BasketItem basketItem = repository.findById(basketItemId).get();
        repository.delete(basketItem);
    }

    public ProductDto getProductById(String productId){
        return restTemplate.getForEntity(PRODUCT_SERVICE + productId , ProductDto.class).getBody();
    }

    public BasketItem findBasketEntity(String basketItemId){
        BasketItem basketItem =repository.findById(Long.valueOf(basketItemId)).get();
        return basketItem;

    }

    public BasketItemDto toDto(BasketItem basketItem) {
        return BasketItemDto.builder()
                .basketItemId(basketItem.getBasketItemId())
                .basketItemAmount(basketItem.getBasketItemAmount())
                .count(basketItem.getCount())
                .product(getProductById(String.valueOf(basketItem.getProductId())))
                .build();
    }


}
