package com.recharged.backend.utility;

import org.springframework.stereotype.Component;

import com.recharged.backend.dto.ProductResponseDTO;
import com.recharged.backend.entity.Product;

@Component
public class ProductResponseMapper {
    public static ProductResponseDTO map(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setVendor(product.getVendor());
        dto.setCategory(product.getCategory());
        dto.setSubCategory(product.getSubCategory());
        dto.setCondition(product.getCondition());
        dto.setDescription(product.getDescription());
        dto.setBlurb(product.getBlurb());
        dto.setPrice(product.getPrice());
        dto.setCost(product.getCost());
        dto.setStock(product.getStock());
        dto.setItemNumber(product.getItemNumber());
        dto.setUpc(product.getUpc());
        dto.setModelNumber(product.getModelNumber());
        dto.setSerialNumber(product.getSerialNumber());
        dto.setProductImage(product.getProductImage());
        dto.setTags(product.getTags());
        dto.setProductImages(product.getProductImages());
        dto.setOriginalPackage(product.getOriginalPackage());
        dto.setNotes(product.getNotes());

        return dto;
    }
}