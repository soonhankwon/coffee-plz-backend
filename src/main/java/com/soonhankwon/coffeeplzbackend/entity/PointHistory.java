package com.soonhankwon.coffeeplzbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "point_history")
public class PointHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PointType pointType;

    @Column(name = "point", nullable = false)
    private Long point;

    public enum PointType {
        CHARGE, USAGE
    }
    public PointHistory (User user, PointType type, Long point) {
        this.user = user;
        this.pointType = type;
        this.point = point;
    }
}
