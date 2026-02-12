package com.learn.stripepay.paymentservice.controller;

import com.learn.stripepay.paymentservice.client.OrderClient;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook")
@RequiredArgsConstructor
@Slf4j
public class StripeWebhookController {

    private final OrderClient orderClient;

    @Value("${stripe.webhook.secret}")
    private String endpointSecret;

    @PostMapping
    public String handleStripeWebhook(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader) {

        Event event;

        log.info("Received Stripe webhook");
        log.info("Signature: {}", sigHeader);

        try {
            event = Webhook.constructEvent(
                    payload,
                    sigHeader,
                    endpointSecret
            );

            log.info("Event Type: {}", event.getType());

        } catch (SignatureVerificationException e) {
            log.error("Invalid Stripe signature");
            return "Invalid signature";
        }

        // check Success or not
        if ("checkout.session.completed".equals(event.getType())) {

            Session session = (Session) event
                    .getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if (session == null) {
                log.error("Session is null");
                return "Session null";
            }

            String orderIdStr =
                    session.getMetadata().get("orderId");

            if (orderIdStr == null) {
                return "OrderId metadata missing";
            }

            Long orderId = Long.valueOf(orderIdStr);
            String paymentId = session.getId();

            log.info("Payment SUCCESS for Order: {}", orderId);

            orderClient.updatePayment(
                    orderId,
                    "PAID",
                    paymentId
            );
        }

        //checkout failed or expired
        if ("checkout.session.expired".equals(event.getType())) {

            Session session = (Session) event
                    .getDataObjectDeserializer()
                    .getObject()
                    .orElse(null);

            if (session == null) {
                log.error("Session is null");
                return "Session null";
            }

            String orderIdStr =
                    session.getMetadata().get("orderId");

            if (orderIdStr == null) {
                log.error("OrderId metadata missing");
                return "OrderId metadata missing";
            }

            Long orderId = Long.valueOf(orderIdStr);

            log.info("Payment FAILED for Order: {}", orderId);

            orderClient.updatePayment(
                    orderId,
                    "FAILED",
                    session.getId()
            );
        }

        return "Webhook processed";
    }
}
