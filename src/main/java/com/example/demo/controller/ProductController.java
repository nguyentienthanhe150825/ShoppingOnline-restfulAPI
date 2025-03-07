package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.Product;
import com.example.demo.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ProductController {
    // Dependency Inject: Constructor
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createNewProduct(@Valid @RequestBody Product createProduct) {
        // Upload Product Image

        // Save Product in database
        Product product = this.productService.handleCreateProduct(createProduct);

        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

}
