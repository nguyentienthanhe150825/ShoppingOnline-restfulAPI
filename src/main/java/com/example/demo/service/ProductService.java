package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.Product;
import com.example.demo.repository.ProductRepository;

@Service
public class ProductService {
    // Dependency Inject: Constructor
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product handleCreateProduct(Product product) {
        return this.productRepository.save(product);
    }

}
