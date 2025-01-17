package com.recharged.backend.controller;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.dto.SimpleProductResponseDTO;
import com.recharged.backend.entity.Product;
import com.recharged.backend.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add/products")
    public ResponseEntity<List<Product>> addProducts(@RequestBody List<ProductRequestDTO> productRequests) {
        List<Product> createdProducts = productService.addProducts(productRequests);
        return ResponseEntity.ok(createdProducts);
    }

    @GetMapping("/get/products/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/get/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/get/products/{category}")
    public ResponseEntity<List<SimpleProductResponseDTO>> getProductsByCategory(@PathVariable String category) {
        List<SimpleProductResponseDTO> products = productService.findAllByCategory(category);
        return ResponseEntity.ok(products);
    }
}
