package com.recharged.backend.util;

import org.springframework.stereotype.Component;

import com.recharged.backend.dto.ProductResponseDTO;
import com.recharged.backend.entity.Product;

@Component
public class ProductResponseMapper {
    public static ProductResponseDTO map(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setBrand(product.getBrand());
        dto.setCondition(product.getCondition());
        dto.setColor(product.getColor());
        dto.setBlurb(product.getBlurb());
        dto.setDescription(product.getDescription());
        dto.setNotes(product.getNotes());
        dto.setCategory(product.getCategory());
        dto.setSubCategory(product.getSubCategory());
        dto.setUnitCost(product.getUnitCost());
        dto.setUnitAmount(product.getUnitAmount());
        dto.setStripeProductId(product.getStripeProductId());
        dto.setStripePriceId(product.getStripePriceId());
        dto.setStripeTaxCodeId(product.getStripeTaxCodeId());
        dto.setStripeStatementDescription(product.getStripeStatementDescription());
        dto.setStock(product.getStock());
        dto.setSku(product.getSku());
        dto.setModelNumber(product.getModelNumber());
        dto.setSerialNumber(product.getSerialNumber());
        dto.setTags(product.getTags());
        dto.setProductImages(product.getProductImages());

        return dto;
    }
}