package com.example.stock.domain.product.impl;

import com.example.stock.domain.category.impl.Category;
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

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long productId;
    private String name;
    private int stock;
    private double price;
    @OneToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "categoryId")
    private Category category;
}
