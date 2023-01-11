package com.soonhankwon.coffeeplzbackend.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    @Setter
    private String loginId;

    private String password;

    @Setter
    private String email;

    private Long point;
}