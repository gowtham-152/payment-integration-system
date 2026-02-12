package com.learn.stripepay.paymentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "ORDER-SERVICE")
public interface OrderClient {

    @PutMapping("/orders/update-payment")
    void updatePayment(
            @RequestParam Long orderId,
            @RequestParam String status,
            @RequestParam String paymentId);
}
