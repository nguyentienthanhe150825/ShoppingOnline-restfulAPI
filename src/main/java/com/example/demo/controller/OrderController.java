package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Order;
import com.example.demo.domain.response.order.ResOrderDTO;
import com.example.demo.service.OrderService;
import com.example.demo.util.exception.InvalidException;

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
    public ResponseEntity<ResOrderDTO> createNewOrder(@Valid @RequestBody Order createOrder) {
        Order newOrder = this.orderService.handleCreateOrder(createOrder);

        // convert Order -> ResCreateOrderDTO
        ResOrderDTO orderDTO = this.orderService.convertToResOrderDTO(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<ResOrderDTO> getOrderById(@PathVariable("id") long id) throws InvalidException {
        Order order = this.orderService.handleGetOrderById(id);
        if (order == null) {
            throw new InvalidException("Order with id = " + id + " not exist");
        }

        ResOrderDTO orderDTO = this.orderService.convertToResOrderDTO(order);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }

}
