package com.soonhankwon.coffeeplzbackend.entity;

import javax.persistence.*;

@Entity
public class PointHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", nullable = false)
    @Enumerated
    private PointType pointType;

    @Column(name = "point", nullable = false)
    private Long point;

    public enum PointType {
        CHARGE, USAGE
    }
}
