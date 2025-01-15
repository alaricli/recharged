package com.recharged.backend.service;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.entity.Product;
import com.recharged.backend.repository.ProductRepository;
import com.recharged.backend.utility.ProductRequestMapper;
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

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
