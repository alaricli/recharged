package com.recharged.backend.service;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.dto.SimpleProductResponseDTO;
import com.recharged.backend.entity.Product;
import com.recharged.backend.repository.ProductRepository;
import com.recharged.backend.util.ProductRequestMapper;
import com.recharged.backend.util.SimpleProductResponseMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> addProducts(List<ProductRequestDTO> productRequests) {
        List<Product> newProducts = new ArrayList<>();
        for (ProductRequestDTO productRequestDTO : productRequests) {
            Product newProduct = ProductRequestMapper.map(productRequestDTO);
            newProducts.add(newProduct);
        }

        return productRepository.saveAll(newProducts);
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product findBySku(String sku) {
        return productRepository.findBySku(sku).orElse(null);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<SimpleProductResponseDTO> findAllByCategory(String category) {
        List<Product> products = productRepository.findAllByCategory(category);
        List<SimpleProductResponseDTO> simpleProductResponseDTOS = new ArrayList<>();
        for (Product product : products) {
            SimpleProductResponseDTO productResponseDTO = SimpleProductResponseMapper.toSimpleProductResponse(product);
            simpleProductResponseDTOS.add(productResponseDTO);
        }
        return simpleProductResponseDTOS;
    }
}
