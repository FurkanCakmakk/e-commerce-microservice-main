package com.example.basket.domain.basket.impl.basketitem;

import com.example.basket.domain.basket.impl.Basket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long basketItemId;
    private long productId;
    private int count;
    private double basketItemAmount;
    @ManyToOne
    @JoinColumn(name = "basketId")
    private Basket basket;
}
