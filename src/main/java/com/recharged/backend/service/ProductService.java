package com.recharged.backend.service;

import com.recharged.backend.dto.ProductRequestDTO;
import com.recharged.backend.dto.ProductResponseDTO;
import com.recharged.backend.dto.SimpleProductResponseDTO;
import com.recharged.backend.entity.Product;
import com.recharged.backend.repository.ProductRepository;
import com.recharged.backend.util.ProductRequestMapper;
import com.recharged.backend.util.ProductResponseMapper;
import com.recharged.backend.util.SimpleProductResponseMapper;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
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

                // Step 3: Create Stripe Price
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
        return productRepository.save(newProduct);
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public ProductResponseDTO findBySku(String sku) {
        return productRepository.findBySku(sku)
                .map(ProductResponseMapper::map)
                .orElse(null);
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

    public Page<SimpleProductResponseDTO> findAllByCategoryPaginated(
            String category, int page, int size) {

        // Create a Pageable object with pagination parameters
        Pageable pageable = PageRequest.of(page, size);

        // Get a page of products from the repository
        Page<Product> productPage = productRepository.findAllByCategory(category, pageable);

        // Convert Page<Product> to Page<SimpleProductResponseDTO>
        return productPage.map(product -> SimpleProductResponseMapper.toSimpleProductResponse(product));
    }
}
