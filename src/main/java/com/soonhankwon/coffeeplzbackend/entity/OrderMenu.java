package com.soonhankwon.coffeeplzbackend.entity;

import javax.persistence.*;

@Entity
public class OrderMenu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private String menuName;

    @Column(nullable = false)
    private Long price;

    @Column(nullable = false)
    private Long menuSizeId;

    @Column(nullable = false)
    private Long menuOptionId;

    @Column(nullable = false)
    private String menuOptionName = "No Option";

    @Column(nullable = false)
    private Long menuOptionPrice = 0L;

    @Column(nullable = false)
    private Long menuQuantity;
}
