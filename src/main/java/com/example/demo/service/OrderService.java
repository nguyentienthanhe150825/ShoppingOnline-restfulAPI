package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Order;
import com.example.demo.domain.User;
import com.example.demo.domain.response.order.ResOrderDTO;
import com.example.demo.repository.OrderRepository;

@Service
public class OrderService {
    // Dependency Inject: Constructor
    private final OrderRepository orderRepository;
    private final UserService userService;

    public OrderService(OrderRepository orderRepository, UserService userService) {
        this.orderRepository = orderRepository;
        this.userService = userService;
    }

    public Order handleCreateOrder(Order order) {
        // check user
        if (order.getUser() != null) {
            User user = this.userService.handleGetUserById(order.getUser().getId());
            if (user != null) {
                order.setUser(user);
            } else {
                order.setUser(null);
            }
        }
        return this.orderRepository.save(order);
    }

    public ResOrderDTO convertToResOrderDTO(Order order) {
        ResOrderDTO resOrderDTO = new ResOrderDTO();
        resOrderDTO.setId(order.getId());
        resOrderDTO.setTotalPrice(order.getTotalPrice());
        resOrderDTO.setStatus(order.getStatus());
        resOrderDTO.setShippingAddress(order.getShippingAddress());
        resOrderDTO.setPaymentMethod(order.getPaymentMethod());
        resOrderDTO.setTrackingNumber(order.getTrackingNumber());
        resOrderDTO.setOrderDate(order.getOrderDate());

        ResOrderDTO.UserOrder userDTO = new ResOrderDTO.UserOrder();

        if (order.getUser() != null) {
            userDTO.setId(order.getUser().getId());
            userDTO.setName(order.getUser().getName());

            resOrderDTO.setUser(userDTO);
        }

        return resOrderDTO;
    }

    public Order handleGetOrderById(long id) {
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            return orderOptional.get();
        }
        return null;
    }

}
