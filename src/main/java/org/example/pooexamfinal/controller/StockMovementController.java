package org.example.pooexamfinal.controller;

import org.example.pooexamfinal.dto.StockMovementCreateDTO;
import org.example.pooexamfinal.dto.StockMovementResponseDTO;
import org.example.pooexamfinal.model.MovementType;
import org.example.pooexamfinal.model.StockMovement;
import org.example.pooexamfinal.service.StockMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StockMovementController {
    private final StockMovementService stockMovementService;

    public StockMovementController(StockMovementService stockMovementService) {
        this.stockMovementService = stockMovementService;
    }

    @GetMapping("/stock-movements")
    public List<StockMovementResponseDTO> getAllStockMovements(@RequestParam(required = false) MovementType type) {
        return stockMovementService.getAllStockMovements(type).stream()
                .map(StockMovementResponseDTO::from)
                .toList();
    }

    @PostMapping("/stock-movements")
    public ResponseEntity<StockMovementResponseDTO> createStockMovement(@RequestBody StockMovementCreateDTO dto) {
        StockMovement created = stockMovementService.createStockMovement(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(StockMovementResponseDTO.from(created));
    }

    @GetMapping("/products/{id}/stock-movements")
    public List<StockMovementResponseDTO> getStockMovementsByProductId(@PathVariable String id) {
        return stockMovementService.getStockMovementsByProductId(id).stream()
                .map(StockMovementResponseDTO::from)
                .toList();
    }
}