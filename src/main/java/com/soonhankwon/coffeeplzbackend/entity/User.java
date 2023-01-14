package com.soonhankwon.coffeeplzbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    private String email;

    @Column(nullable = false)
    private Long point = 0L;

    public User(String loginId, String password, String email, Long point) {
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.point = point;
    }

    public void setUserPoint(Long point) {
        this.point = point;
    }
}