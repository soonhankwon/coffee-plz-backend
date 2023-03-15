package com.soonhankwon.coffeeplzbackend.domain;

import com.soonhankwon.coffeeplzbackend.common.domain.BaseTimeEntity;
import com.soonhankwon.coffeeplzbackend.dto.request.SignupRequestDto;
import com.soonhankwon.coffeeplzbackend.common.exception.ErrorCode;
import com.soonhankwon.coffeeplzbackend.common.exception.RequestException;
import com.soonhankwon.coffeeplzbackend.dto.response.UserResponseDto;
import lombok.*;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    public User(Long id, String loginId, String password, String email) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
    }

    public User(Long id, String loginId, String password, String email, Long point) {
        this.id = id;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.point = point;
    }

    private boolean isChargePointOverMinPoint(Long chargePoint) {
        if (chargePoint <= MIN_POINT)
            throw new RequestException(ErrorCode.POINT_INVALID);
        return true;
    }

    public void setUserPointWithValidChargePoint(Long chargePoint) {
        if (isChargePointOverMinPoint(chargePoint)) {
            this.point += chargePoint;
        }
    }

    public boolean isUserHasEnoughPoint(Order order) {
        if (this.point < order.getTotalPrice())
            throw new RequestException(ErrorCode.POINT_INSUFFICIENT);
        return this.point >= order.getTotalPrice();
    }

    public void paid(Order order) {
        this.point -= order.getTotalPrice();
    }

    public static UserResponseDto createUserResDto(User user) {
        return new UserResponseDto(user.loginId, user.email, user.point);
    }
}