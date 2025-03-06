package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Order;
import com.example.demo.domain.response.order.ResCreateOrderDTO;
import com.example.demo.service.OrderService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class OrderController {
    // Dependency Inject: Constructor
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<ResCreateOrderDTO> createNewOrder(@Valid @RequestBody Order createOrder) {
        Order newOrder = this.orderService.handleCreateOrder(createOrder);

        // convert Order -> ResCreateOrderDTO
        ResCreateOrderDTO orderDTO = this.orderService.convertToResCreateOrderDTO(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

}
