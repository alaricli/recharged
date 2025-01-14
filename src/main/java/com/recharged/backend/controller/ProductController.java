package com.recharged.backend.controller;

import com.recharged.backend.entity.Product;
import com.recharged.backend.service.ProductService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @GetMapping
    public List<Product> getAllProducts() {
        throw new UnsupportedOperationException("Not implemented");
    }
}
