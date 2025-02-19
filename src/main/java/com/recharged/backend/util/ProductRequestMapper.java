package com.recharged.backend.util;

import org.springframework.stereotype.Component;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.entity.Product;

@Component
public class ProductRequestMapper {
    public static Product map(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getProductName());
        product.setBrand(dto.getBrand());
        product.setCondition(dto.getCondition());
        product.setColor(dto.getColor());
        product.setBlurb(dto.getBlurb());
        product.setDescription(dto.getDescription());
        product.setNotes(dto.getNotes());
        product.setCategory(dto.getCategory());
        product.setSubCategory(dto.getSubCategory());
        product.setUnitCostCAD(dto.getUnitCostCAD());
        product.setStock(dto.getStock());
        product.setSku(dto.getSku());
        product.setModelNumber(dto.getModelNumber());
        product.setSerialNumber(dto.getSerialNumber());
        product.setTags(dto.getTags());
        product.setProductImages(dto.getProductImages());

        return product;
    }
}
