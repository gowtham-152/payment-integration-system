package com.learn.stripepay.orderservice.service;

import com.learn.stripepay.orderservice.client.PaymentClient;
import com.learn.stripepay.orderservice.dto.PaymentRequest;
import com.learn.stripepay.orderservice.entity.Order;
import com.learn.stripepay.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;
    private final PaymentClient paymentClient;

    public Order save(Order order) {

        // Save order first
        Order savedOrder = repository.save(order);

        // Prepare payment request
        PaymentRequest request = new PaymentRequest();
        request.setOrderId(savedOrder.getId());
        request.setAmount(savedOrder.getAmount());
        request.setProductName(savedOrder.getProductName());
        request.setCurrency("usd");

        // Call payment service
        String paymentUrl = paymentClient.createPayment(request);

        System.out.println("Payment URL: " + paymentUrl);

        return savedOrder;
    }

    public List<Order> getAllOrders() {
        return repository.findAll();
    }

    public Order updatePayment(Long orderId, String status, String paymentId) {

        Order order = repository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(status);
        order.setPaymentId(paymentId);

        return repository.save(order);
    }
}
