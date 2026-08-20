package org.example.pooexamfinal.dto;

import org.example.pooexamfinal.model.Product;

import java.math.BigDecimal;

public record ProductResponse(
        String id,
        String name,
        String description,
        BigDecimal unitPrice
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getUnitPrice()
        );
    }
}
