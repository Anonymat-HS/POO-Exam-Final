package org.example.pooexamfinal.service;

import org.example.pooexamfinal.exception.ProductNotFoundException;
import org.example.pooexamfinal.model.Product;
import org.example.pooexamfinal.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public boolean productExists(String id) {
        return productRepository.existsById(id);
    }
}
