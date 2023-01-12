package com.soonhankwon.coffeeplzbackend.entity;

import javax.persistence.*;

@Entity
public class MenuSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private String name;
}
