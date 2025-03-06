package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {
    // Dependency Inject: Constructor
    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    
}
