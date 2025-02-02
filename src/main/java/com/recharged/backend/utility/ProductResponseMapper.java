package com.recharged.backend.utility;

import org.springframework.stereotype.Component;

import com.recharged.backend.dto.ProductResponseDTO;
import com.recharged.backend.entity.Product;

@Component
public class ProductResponseMapper {
    public static ProductResponseDTO map(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getProductName());
        dto.setVendor(product.getBrand());
        dto.setCategory(product.getCategory());
        dto.setSubCategory(product.getSubCategory());
        dto.setCondition(product.getCondition());
        dto.setDescription(product.getDescription());
        dto.setBlurb(product.getBlurb());
        dto.setPrice(product.getUnitPriceCAD());
        dto.setCost(product.getUnitCostCAD());
        dto.setStock(product.getStock());
        dto.setItemNumber(product.getItemNumber());
        dto.setUpc(product.getUpc());
        dto.setModelNumber(product.getModelNumber());
        dto.setSerialNumber(product.getSerialNumber());
        dto.setProductImage(product.getProductImage());
        dto.setTags(product.getTags());
        dto.setProductImages(product.getProductImages());
        dto.setOriginalPackage(product.getHasOriginalPackaging());
        dto.setNotes(product.getNotes());

        return dto;
    }
}