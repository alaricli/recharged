package com.recharged.backend.service;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.dto.SimpleProductResponseDTO;
import com.recharged.backend.dto.StripePriceRequestDTO;
import com.recharged.backend.entity.Product;
import com.recharged.backend.entity.StripePriceObject;
import com.recharged.backend.repository.ProductRepository;
import com.recharged.backend.util.ProductRequestMapper;
import com.recharged.backend.util.SimpleProductResponseMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final StripeService stripeService;

    public ProductService(ProductRepository productRepository, StripeService stripeService) {
        this.productRepository = productRepository;
        this.stripeService = stripeService;
    }

    // Given ProductRequestDTO, need to:
    // 1. Make a Product (in our database)
    // 2. Make a StripeProduct (in Stripe) using our DTO
    // 3. Make StripePriceIds using the nested StripePriceObjectRequestDTOs
    // and the StripeProduct created so far (Stripe price requires product_id)
    // 4. Attach these StripePriceIds to the Product entity in our database
    // 5. Save the completed Product with all Stripe information in our database
    public List<Product> addProducts(List<ProductRequestDTO> productRequests) {
        List<Product> newProducts = new ArrayList<>();
        for (ProductRequestDTO productRequestDTO : productRequests) {
            Product newProduct = ProductRequestMapper.map(productRequestDTO);
            // make a stripeProduct, then attach the stripeId to our product
            String associatedStripeProductId = stripeService.createStripeProduct(productRequestDTO);
            newProduct.setStripeProductId(associatedStripeProductId);
            // make priceIds
            List<StripePriceObject> associatedStripePriceObjects = new ArrayList<>();
            for (StripePriceRequestDTO stripePriceRequestDTO : productRequestDTO.getStripePriceIds()) {
                StripePriceObject createdPriceObject = stripeService.createStripePriceObject(stripePriceRequestDTO,
                        associatedStripeProductId);
                associatedStripePriceObjects.add(createdPriceObject);
            }
            ;
            newProduct.setStripePriceIds(associatedStripePriceObjects);
            newProducts.add(newProduct);
        }

        return productRepository.saveAll(newProducts);
    }

    public Product addProduct() {
        throw new UnsupportedOperationException("not implemented");
    }

    public Product findById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    public Product findBySku(String sku) {
        return productRepository.findBySku(sku).orElse(null);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<SimpleProductResponseDTO> findAllByCategory(String category) {
        List<Product> products = productRepository.findAllByCategory(category);
        List<SimpleProductResponseDTO> simpleProductResponseDTOS = new ArrayList<>();
        for (Product product : products) {
            SimpleProductResponseDTO productResponseDTO = SimpleProductResponseMapper.toSimpleProductResponse(product);
            simpleProductResponseDTOS.add(productResponseDTO);
        }
        return simpleProductResponseDTOS;
    }
}
