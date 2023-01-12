package com.soonhankwon.coffeeplzbackend.entity;


import javax.persistence.*;

@Entity
public class Order extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long totalPrice;

    @Column
    private String address;

    @Column
    private String requirement;

    @Column(nullable = false)
    private String orderState;
}
