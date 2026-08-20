package org.example.pooexamfinal.dto;

import java.math.BigDecimal;

public record StockResponse(
        String id,
        String name,
        long stock
) {
}
