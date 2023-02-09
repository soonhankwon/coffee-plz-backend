package com.soonhankwon.coffeeplzbackend.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private PointHistory (User user, PointType type, Long point) {
        this.user = user;
        this.pointType = type;
        this.point = point;
    }
    public static PointHistory createPointHistory(User user, PointHistory.PointType type, Long point) {
        return new PointHistory(user, type, point);
    }
}
