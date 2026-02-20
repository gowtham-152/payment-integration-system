package com.learn.stripepay.userservice.service;

import com.learn.stripepay.userservice.dto.UserRequest;
import com.learn.stripepay.userservice.dto.UserResponse;
import com.learn.stripepay.userservice.entity.User;
import com.learn.stripepay.userservice.repository.UserRepository;
import com.learn.stripepay.userservice.kafka.producer.*;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {

        CustomerCreateParams params =
                CustomerCreateParams.builder()
                        .setName(userRequest.getName())
                        .setEmail(userRequest.getEmail())
                        .build();

        Customer customer;
        try {
            customer = Customer.create(params);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to create Stripe customer: " + e.getMessage()
            );
        }

        User user = User.builder()
                .name(userRequest.getName())
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .stripeCustomerId(customer.getId())
                .build();

        userRepository.save(user);


        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .stripeCustomerId(user.getStripeCustomerId())
                .build();
    }

    public UserResponse getUserById(Long id) {

        User user = (User) userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found with id: " + id)
                );

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .stripeCustomerId(user.getStripeCustomerId())
                .build();
    }

}
