package com.recharged.backend.dto;

import java.math.BigDecimal;
import java.util.List;

public class ProductRequestDTO {
    private String productName;
    private Boolean active;
    private String brand;
    private String condition;
    private String color;
    private Boolean hasOriginalPackaging;
    private Boolean hasOriginalAccessories;
    private String blurb;
    private String description;
    private String notes;
    private String statementDescription;
    private String category;
    private String subCategory;
    private BigDecimal unitCostCAD;
    private Integer stock;
    private String itemNumber;
    private String upc;
    private String sku;
    private String modelNumber;
    private String serialNumber;
    private String mainImage;
    private List<String> tags;
    private List<String> productImages;
    private List<StripePriceRequestDTO> stripePriceIds;

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

    public Boolean getHasOriginalPackaging() {
        return hasOriginalPackaging;
    }

    public void setHasOriginalPackaging(Boolean hasOriginalPackaging) {
        this.hasOriginalPackaging = hasOriginalPackaging;
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

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getUpc() {
        return upc;
    }

    public void setUpc(String upc) {
        this.upc = upc;
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
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getHasOriginalAccessories() {
        return hasOriginalAccessories;
    }

    public void setHasOriginalAccessories(Boolean hasOriginalAccessories) {
        this.hasOriginalAccessories = hasOriginalAccessories;
    }

    public String getStatementDescription() {
        return statementDescription;
    }

    public void setStatementDescription(String statementDescription) {
        this.statementDescription = statementDescription;
    }

    public List<StripePriceRequestDTO> getStripePriceIds() {
        return stripePriceIds;
    }

    public void setStripePriceIds(List<StripePriceRequestDTO> stripePriceIds) {
        this.stripePriceIds = stripePriceIds;
    }
}
