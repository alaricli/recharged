package com.recharged.backend.dto;

import java.util.List;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductResponseDTO {
    private Long id;
    private String productName;
    private String brand;
    private String condition;
    private String color;
    private String blurb;
    private String description;
    private String notes;
    private String statementDescription;
    private String category;
    private String subCategory;
    private BigDecimal unitCostCAD;
    private Boolean active;
    private LocalDateTime created;
    private String stripeProductId;
    private String stripeTaxCodeId;
    private Integer stock;
    private String sku;
    private String modelNumber;
    private String serialNumber;
    private List<String> tags;
    private List<String> productImages;
    private List<StripePriceResponseDTO> stripePriceIds;

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public BigDecimal getUnitCostCAD() {
        return unitCostCAD;
    }

    public void setUnitCostCAD(BigDecimal unitCostCAD) {
        this.unitCostCAD = unitCostCAD;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getStatementDescription() {
        return statementDescription;
    }

    public void setStatementDescription(String statementDescription) {
        this.statementDescription = statementDescription;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getStripeProductId() {
        return stripeProductId;
    }

    public void setStripeProductId(String stripeProductId) {
        this.stripeProductId = stripeProductId;
    }

    public String getStripeTaxCodeId() {
        return stripeTaxCodeId;
    }

    public void setStripeTaxCodeId(String stripeTaxCodeId) {
        this.stripeTaxCodeId = stripeTaxCodeId;
    }

    public List<StripePriceResponseDTO> getStripePriceIds() {
        return stripePriceIds;
    }

    public void setStripePriceIds(List<StripePriceResponseDTO> stripePriceIds) {
        this.stripePriceIds = stripePriceIds;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getMainImage() {
        return productImages != null && !productImages.isEmpty() ? productImages.get(0) : null;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<String> productImages) {
        this.productImages = productImages;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}