package com.soonhankwon.coffeeplzbackend.repository;

import com.soonhankwon.coffeeplzbackend.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
