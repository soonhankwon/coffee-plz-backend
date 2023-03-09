package com.soonhankwon.coffeeplzbackend.entity;

import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.exception.RequestException;
import lombok.*;

import javax.persistence.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    private static final Long MIN_POINT = 0L;

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

    public User(SignupRequestDto dto) {
        this.loginId = dto.getLoginId();
        this.password = dto.getPassword();
        this.email = dto.getEmail();
        this.point = MIN_POINT;
    }

    public void setUserPointWithValidChargePoint(Long chargePoint) {
        if (chargePoint > 0)
            this.point += chargePoint;
        if (chargePoint <= 0)
            throw new RequestException(ErrorCode.POINT_INVALID);
    }

    public void setUserPointWithSufficientPoint(Long userPoint, Long orderPoint) {
        if (userPoint >= orderPoint)
            this.point = userPoint - orderPoint;
        if (userPoint < orderPoint)
            throw new RequestException(ErrorCode.POINT_INSUFFICIENT);
    }

    public Long getId() {
        return this.id;
    }
    public Long getPoint() {
        return this.point;
    }
    public String getLoginId() {
        return this.loginId;
    }
    public String getEmail() {
        return this.email;
    }
}