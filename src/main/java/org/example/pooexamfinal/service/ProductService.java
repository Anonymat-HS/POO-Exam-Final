package org.example.pooexamfinal.service;

import org.example.pooexamfinal.dto.StockResponse;
import org.example.pooexamfinal.exception.ProductNotFoundException;
import org.example.pooexamfinal.model.Product;
import org.example.pooexamfinal.model.StockMovement;
import org.example.pooexamfinal.repository.ProductRepository;
import org.example.pooexamfinal.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public ProductService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public Product getProductById(String id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    public boolean productExists(String id) {
        return productRepository.existsById(id);
    }

    public StockResponse getProductStock(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        List<StockMovement> movements = stockMovementRepository.findByProductId(id);
        int stock = movements.stream()
                .mapToInt(m -> "IN".equals(m.getMovementType().name()) ? m.getQuantity() : -m.getQuantity())
                .sum();
        return new StockResponse(product.getId(), product.getName(), stock);
    }
}
