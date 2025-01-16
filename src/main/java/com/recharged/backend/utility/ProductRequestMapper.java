package com.recharged.backend.utility;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.entity.Product;

public class ProductRequestMapper {
    public static Product map(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setVendor(dto.getVendor());
        product.setCategory(dto.getCategory());
        product.setCondition(dto.getCondition());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCost(dto.getCost());
        product.setStock(dto.getStock());
        product.setItemNumber(dto.getItemNumber());
        product.setUpc(dto.getUpc());
        product.setModelNumber(dto.getModelNumber());
        product.setSerialNumber(dto.getSerialNumber());
        product.setProductImage(dto.getProductImage());
        product.setTags(dto.getTags());
        product.setProductImages(dto.getProductImages());
        product.setOriginalPackage(dto.getOriginalPackage());
        product.setNotes(dto.getNotes());

        return product;
    }
}
