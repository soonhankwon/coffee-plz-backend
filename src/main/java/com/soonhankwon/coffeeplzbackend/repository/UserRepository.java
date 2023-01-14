package com.soonhankwon.coffeeplzbackend.repository;

import com.soonhankwon.coffeeplzbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByLoginId(String loginId);
    List<User> findAll();
    boolean existsByLoginId(String loginId);
}
