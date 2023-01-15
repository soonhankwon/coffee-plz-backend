package com.soonhankwon.coffeeplzbackend.entity;

import javax.persistence.*;

@Entity
public class PointHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Long point;

}
