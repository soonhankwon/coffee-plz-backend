package com.soonhankwon.coffeeplzbackend.repository;

import com.soonhankwon.coffeeplzbackend.domain.Order;
import com.soonhankwon.coffeeplzbackend.domain.OrderStatus;
import com.soonhankwon.coffeeplzbackend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAll();
    Optional<Order> findOrderByUserAndStatus(User user, OrderStatus type);
    Optional<Order> findByUserIdAndStatus(Long userId, OrderStatus type);
    boolean existsByUserIdAndStatus(Long userId, OrderStatus type);
}
