package com.recharged.backend.util;

import org.springframework.stereotype.Component;

import com.recharged.backend.dto.ProductResponseDTO;
import com.recharged.backend.entity.Product;

@Component
public class ProductResponseMapper {
    public static ProductResponseDTO map(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setProductName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setCondition(product.getCondition());
        dto.setColor(product.getColor());
        dto.setHasOriginalPackaging(product.getHasOriginalPackaging());
        dto.setBlurb(product.getBlurb());
        dto.setDescription(product.getDescription());
        dto.setNotes(product.getNotes());
        dto.setCategory(product.getCategory());
        dto.setSubCategory(product.getSubCategory());
        dto.setUnitCostCAD(product.getUnitCostCAD());
        dto.setUnitPriceCAD(product.getUnitPriceCAD());
        dto.setStock(product.getStock());
        dto.setItemNumber(product.getItemNumber());
        dto.setUpc(product.getUpc());
        dto.setSku(product.getSku());
        dto.setModelNumber(product.getModelNumber());
        dto.setSerialNumber(product.getSerialNumber());
        dto.setMainImage(product.getMainImage());
        dto.setTags(product.getTags());
        dto.setProductImages(product.getProductImages());

        return dto;
    }
}