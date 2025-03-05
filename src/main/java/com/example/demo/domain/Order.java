package com.example.demo.domain;

import java.time.Instant;

import com.example.demo.util.constant.PaymentMethodEnum;
import com.example.demo.util.constant.StatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    private double totalPrice;

    @Enumerated(EnumType.STRING)
    private StatusEnum status;

    @Column(columnDefinition = "MEDIUMTEXT")
    private String shippingAddress;

    @Enumerated(EnumType.STRING)
    private PaymentMethodEnum paymentMethod;

    private String trackingNumber;

    private Instant orderDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @PrePersist
    public void handleBeforeCreate() {
        this.orderDate = Instant.now();
    }
}
