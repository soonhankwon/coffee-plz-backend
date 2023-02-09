package com.soonhankwon.coffeeplzbackend.entity;

import com.soonhankwon.coffeeplzbackend.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.exception.RequestException;
import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private Long version;

    @Column(name = "login_id")
    private String loginId;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "point")
    private Long point;

    public User(String loginId, String password, String email, Long point) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.point = point;
    }

    public void setUserPointWithValidChargePoint(Long userPoint, Long chargePoint) {
        if (chargePoint > 0)
            this.point = userPoint + chargePoint;
        else
            throw new RequestException(ErrorCode.POINT_INVALID);
    }

    public void setUserPointWithSufficientPoint(Long userPoint, Long orderPoint) {
        if (userPoint >= orderPoint)
            this.point = userPoint - orderPoint;
        else
            throw new RequestException(ErrorCode.POINT_INSUFFICIENT);
    }
}