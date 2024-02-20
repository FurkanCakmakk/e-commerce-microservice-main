package com.example.basket.domain.basket.impl;

import com.example.basket.domain.basket.api.basketitem.BasketItemDto;
import com.example.basket.domain.basket.impl.basketitem.BasketItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long basketId;
    private double totalAmount;
    private int status;
    private long userId;
    @OneToMany(mappedBy = "basket" , cascade = CascadeType.ALL)
    private List<BasketItem> basketItemList;
}
