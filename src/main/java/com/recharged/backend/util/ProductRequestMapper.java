package com.recharged.backend.util;

import org.springframework.stereotype.Component;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.entity.Product;

@Component
public class ProductRequestMapper {
    public static Product map(ProductRequestDTO dto) {
        Product product = new Product();
        product.setProductName(dto.getProductName());
        product.setBrand(dto.getBrand());
        product.setCondition(dto.getCondition());
        product.setColor(dto.getColor());
        product.setHasOriginalPackaging(dto.getHasOriginalPackaging());
        product.setBlurb(dto.getBlurb());
        product.setDescription(dto.getDescription());
        product.setNotes(dto.getNotes());
        product.setCategory(dto.getCategory());
        product.setSubCategory(dto.getSubCategory());
        product.setUnitCostCAD(dto.getUnitCostCAD());
        product.setUnitPriceCAD(dto.getUnitPriceCAD());
        product.setStock(dto.getStock());
        product.setItemNumber(dto.getItemNumber());
        product.setUpc(dto.getUpc());
        product.setSku(dto.getSku());
        product.setModelNumber(dto.getModelNumber());
        product.setSerialNumber(dto.getSerialNumber());
        product.setMainImage(dto.getMainImage());
        product.setTags(dto.getTags());
        product.setProductImages(dto.getProductImages());

        return product;
    }
}
