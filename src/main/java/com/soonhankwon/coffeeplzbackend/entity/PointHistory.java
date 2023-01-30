package com.soonhankwon.coffeeplzbackend.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Entity
@Table(name = "point_history", indexes = @Index(name = "idx_user_id", columnList = "user_id"))
public class PointHistory extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
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
