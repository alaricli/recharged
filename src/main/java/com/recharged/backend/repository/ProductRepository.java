package com.recharged.backend.repository;

import com.recharged.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findByName(String name);

    List<Product> findAllByCategory(String category);

    Optional<Product> findBySku(String sku);
}
