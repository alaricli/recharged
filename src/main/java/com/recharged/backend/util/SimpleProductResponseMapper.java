package com.recharged.backend.util;

import org.springframework.stereotype.Component;

import com.recharged.backend.dto.SimpleProductResponseDTO;
import com.recharged.backend.entity.Product;

@Component
public class SimpleProductResponseMapper {
  public static SimpleProductResponseDTO toSimpleProductResponse(Product product) {
    SimpleProductResponseDTO dto = new SimpleProductResponseDTO();
    dto.setSku(product.getSku());
    dto.setProductName(product.getName());
    dto.setUnitPrice(product.getUnitPriceCAD());
    dto.setMainImage(product.getMainImage());
    dto.setBlurb(product.getBlurb());
    dto.setBrand(product.getBrand());
    return dto;
  }
}
