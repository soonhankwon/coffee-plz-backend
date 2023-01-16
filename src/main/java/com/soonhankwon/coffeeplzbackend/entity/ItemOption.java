package com.soonhankwon.coffeeplzbackend.entity;

import javax.persistence.*;

@Entity
@Table(name = "item_option")
public class ItemOption {
    @Id
    @Column(name = "item_option_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;
}
