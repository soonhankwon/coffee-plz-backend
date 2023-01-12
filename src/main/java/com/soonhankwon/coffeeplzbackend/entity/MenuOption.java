package com.soonhankwon.coffeeplzbackend.entity;

import javax.persistence.*;

@Entity
public class MenuOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int price;

}
