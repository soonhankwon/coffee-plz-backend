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

    public void setUserPoint(Long point) {
        this.point = point;
    }
}