package org.example.pooexamfinal.dto;

import org.example.pooexamfinal.model.MovementType;
import org.example.pooexamfinal.model.StockMovement;

import java.time.Instant;

public record StockMovementResponseDTO(
        String id,
        Instant createdAt,
        MovementType movementType,
        int quantity,
        String productId
) {
    public static StockMovementResponseDTO from(StockMovement stockMovement) {
        return new StockMovementResponseDTO(
                stockMovement.getId(),
                stockMovement.getCreatedAt(),
                stockMovement.getMovementType(),
                stockMovement.getQuantity(),
                stockMovement.getProductId()
        );
    }
}