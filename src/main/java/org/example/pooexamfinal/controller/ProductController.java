package org.example.pooexamfinal.controller;

import org.example.pooexamfinal.dto.StockResponse;
import org.example.pooexamfinal.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/{id}/stock")
    public StockResponse getProductStock(@PathVariable String id) {
        return productService.getProductStock(id);
    }
}
