package com.recharged.backend.service;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.dto.SimpleProductResponseDTO;
import com.recharged.backend.dto.StripePriceRequestDTO;
import com.recharged.backend.entity.Product;
import com.recharged.backend.entity.StripePriceObject;
import com.recharged.backend.repository.ProductRepository;
import com.recharged.backend.util.ProductRequestMapper;
import com.recharged.backend.util.SimpleProductResponseMapper;

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
            try {
                // Step 1: Create & Save the Product First
                Product newProduct = addProduct(productRequestDTO);

                // Step 2: Create Stripe Product
                stripeService.createStripeProduct(newProduct);

                // Step 3: Create Stripe Prices
                stripeService.createStripePriceObject(newProduct);

                // Step 4: Save the Product Again (with Stripe details updated)
                newProduct = productRepository.save(newProduct);

                newProducts.add(newProduct);
            } catch (Exception e) {
                System.err.println("Failed to process product: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return newProducts;
    }

    public Product addProduct(ProductRequestDTO requestDTO) {
        Product newProduct = ProductRequestMapper.map(requestDTO);

        List<StripePriceRequestDTO> priceDTOs = requestDTO.getStripePriceIds();
        if (priceDTOs != null && !priceDTOs.isEmpty()) {
            List<StripePriceObject> priceObjects = mapPrices(priceDTOs, newProduct);
            newProduct.setStripePriceIds(priceObjects);
        }

        return productRepository.save(newProduct);
    }

    public Product findById(Long id) {
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

    private List<StripePriceObject> mapPrices(List<StripePriceRequestDTO> prices, Product product) {
        List<StripePriceObject> newPriceObjects = new ArrayList<>();
        for (StripePriceRequestDTO requestDTO : prices) {
            StripePriceObject newPriceObject = mapPriceObject(requestDTO);
            newPriceObject.setProduct(product);
            newPriceObjects.add(newPriceObject);
        }

        return newPriceObjects;
    }

    private StripePriceObject mapPriceObject(StripePriceRequestDTO stripePriceObject) {
        StripePriceObject newStripePriceObject = new StripePriceObject();
        newStripePriceObject.setCurrency(stripePriceObject.getCurrency());
        newStripePriceObject.setUnitAmount(stripePriceObject.getUnitAmount());
        return newStripePriceObject;
    }
}
