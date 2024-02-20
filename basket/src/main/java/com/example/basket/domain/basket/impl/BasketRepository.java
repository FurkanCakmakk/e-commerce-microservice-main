package com.example.basket.domain.basket.impl;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    Basket findBasketByUserIdAndStatusEquals(long customerId, int status);
    Basket findBasketByBasketId(long basketId);
}
