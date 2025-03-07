package com.example.demo.service;

import java.util.Optional;

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

    public Product handleGetProducById(long id) {
        Optional<Product> productOptional = this.productRepository.findById(id);
        if (productOptional.isPresent()) {
            return productOptional.get();
        }
        return null;
    }

    public Product handleCreateProduct(Product product) {
        return this.productRepository.save(product);
    }

    public Product saveProductImage(String uploadImageUrl, long productId) {
        Product product = this.handleGetProducById(productId);
        product.setImage(uploadImageUrl);

        return this.productRepository.save(product);
    }

}
