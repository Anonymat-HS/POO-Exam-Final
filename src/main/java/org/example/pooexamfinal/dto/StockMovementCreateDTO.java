package org.example.pooexamfinal.dto;

import org.example.pooexamfinal.model.MovementType;

public record StockMovementCreateDTO(
        String productId,
        MovementType movementType,
        int quantity
) {
}