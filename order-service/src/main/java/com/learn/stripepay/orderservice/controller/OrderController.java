package com.learn.stripepay.orderservice.controller;

import com.learn.stripepay.orderservice.entity.Order;
import com.learn.stripepay.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    public Order createOrder(
            @RequestBody Order order) {

        return service.save(order);
    }
    @PutMapping("/update-payment")
    public Order updatePayment(
            @RequestParam Long orderId,
            @RequestParam String status,
            @RequestParam String paymentId) {

        return service.updatePayment(orderId, status, paymentId);
    }

    @GetMapping
    public List<Order> allOrders() {
        return service.getAllOrders();
    }
}
