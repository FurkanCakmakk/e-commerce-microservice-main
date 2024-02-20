package com.example.basket.domain.basket.web;

import com.example.basket.domain.basket.api.BasketDto;
import com.example.basket.domain.basket.api.BasketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("baskets")
public class BasketController {
    private final BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @PostMapping
    public BasketResponse addProductToBasket(@RequestBody AddProductRequest productRequest) {
        return toResponse(basketService.addProductToBasket(productRequest.toDto()));
    }

    @DeleteMapping("/{userId}/{basketItemId}")
    public String removerBasketItem(@PathVariable String userId,
                                    @PathVariable String basketItemId) {
        basketService.removeProductFromBasket(userId, basketItemId);

        return "Ürün başarıyla sepetten kaldırıldı";
    }

    @GetMapping("/{userId}")
    public ResponseEntity<BasketResponse>  getBasketByUserId(@PathVariable String userId){
        return ResponseEntity.ok(toResponse(basketService.getBasketByUserId(userId)));
    }

    public BasketResponse toResponse(BasketDto basketDto) {
        return BasketResponse.builder()
                .basketId(basketDto.getBasketId())
                .totalAmount(basketDto.getTotalAmount())
                .status(basketDto.getStatus())
                .userId(basketDto.getUserId())
                .basketItemList(basketDto.getBasketItemList())
                .build();
    }
}
