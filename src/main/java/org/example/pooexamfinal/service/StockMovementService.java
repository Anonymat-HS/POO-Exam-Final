package org.example.pooexamfinal.service;

import org.example.pooexamfinal.dto.StockMovementCreateDTO;
import org.example.pooexamfinal.exception.ProductNotFoundException;
import org.example.pooexamfinal.model.MovementType;
import org.example.pooexamfinal.model.StockMovement;
import org.example.pooexamfinal.repository.ProductRepository;
import org.example.pooexamfinal.repository.StockMovementRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;
    private final ProductRepository productRepository;

    public StockMovementService(StockMovementRepository stockMovementRepository,
                                ProductRepository productRepository) {
        this.stockMovementRepository = stockMovementRepository;
        this.productRepository = productRepository;
    }

    public StockMovement createStockMovement(StockMovementCreateDTO dto) {
        if (!productRepository.existsById(dto.productId())) {
            throw new ProductNotFoundException(dto.productId());
        }
        if (dto.quantity() <= 0) {
            throw new IllegalArgumentException("La quantité doit être strictement positive");
        }
        StockMovement stockMovement = new StockMovement(
                UUID.randomUUID().toString(),
                Instant.now(),
                dto.movementType(),
                dto.quantity(),
                dto.productId()
        );
        return stockMovementRepository.save(stockMovement);
    }

    public List<StockMovement> getAllStockMovements(MovementType type) {
        if (type == null) {
            return stockMovementRepository.findAll();
        }
        return stockMovementRepository.findByMovementType(type);
    }

    public List<StockMovement> getStockMovementsByProductId(String productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }
        return stockMovementRepository.findByProductId(productId);
    }
}