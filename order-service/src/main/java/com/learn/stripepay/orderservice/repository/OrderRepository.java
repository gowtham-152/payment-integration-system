package com.learn.stripepay.orderservice.repository;

import com.learn.stripepay.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
