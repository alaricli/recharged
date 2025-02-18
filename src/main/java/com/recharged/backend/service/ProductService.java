package com.recharged.backend.service;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.dto.SimpleProductResponseDTO;
import com.recharged.backend.dto.StripePriceRequestDTO;
import com.recharged.backend.entity.Product;
import com.recharged.backend.entity.StripePriceObject;
import com.recharged.backend.repository.ProductRepository;
import com.recharged.backend.util.ProductRequestMapper;
import com.recharged.backend.util.SimpleProductResponseMapper;
import com.stripe.exception.StripeException;

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
            String associatedStripeProductId = "";

            // try to create StripeProduct with input
            try {
                associatedStripeProductId = stripeService.createStripeProduct(productRequestDTO);
            } catch (StripeException e) {
                System.err.println("Failed to create Stripe product: " + e.getMessage());
                e.printStackTrace();
            }

            List<StripePriceObject> associatedStripePriceObjects = new ArrayList<>();

            if (!associatedStripeProductId.equals("")) {
                newProduct.setStripeProductId(associatedStripeProductId);

                for (StripePriceRequestDTO stripePriceRequestDTO : productRequestDTO.getStripePriceIds()) {
                    try {
                        StripePriceObject createdPriceObject = stripeService.createStripePriceObject(
                                stripePriceRequestDTO,
                                associatedStripeProductId);
                        associatedStripePriceObjects.add(createdPriceObject);
                    } catch (StripeException e) {
                        System.err.println("Failed to create Stripe Price Object: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            if (!associatedStripePriceObjects.isEmpty()) {
                newProduct.setStripePriceIds(associatedStripePriceObjects);
            }

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
