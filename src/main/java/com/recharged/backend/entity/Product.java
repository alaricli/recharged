package com.recharged.backend.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.List;

import org.hibernate.annotations.UuidGenerator;

@Entity
public class Product {
    @Id
    @GeneratedValue(generator = "UUID")
    @UuidGenerator
    @Column(name = "id", nullable = false, updatable = false)
    private String id;
    private String productName;
    private String brand;
    private String condition;
    private String color;
    private Boolean hasOriginalPackaging;
    @Column(columnDefinition = "TEXT")
    private String blurb;
    @Column(columnDefinition = "TEXT")
    private String description;
    @Column(columnDefinition = "TEXT")
    private String notes;
    private String category;
    private String subCategory;
    private BigDecimal unitCostCAD;
    private BigDecimal unitPriceCAD;
    private Integer stock;
    private String itemNumber;
    private String upc;
    private String sku;
    private String modelNumber;
    private String serialNumber;
    private String productImage;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_tags", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "product_tags")
    private List<String> tags;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "product_images", joinColumns = @JoinColumn(name = "product_id"))
    @Column(name = "product_images")
    private List<String> productImages;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String name) {
        this.productName = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String vendor) {
        this.brand = vendor;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getUnitCostCAD() {
        return unitCostCAD;
    }

    public void setUnitCostCAD(BigDecimal cost) {
        this.unitCostCAD = cost;
    }

    public BigDecimal getUnitPriceCAD() {
        return unitPriceCAD;
    }

    public void setUnitPriceCAD(BigDecimal price) {
        this.unitPriceCAD = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
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

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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

    public Boolean getHasOriginalPackaging() {
        return hasOriginalPackaging;
    }

    public void setHasOriginalPackaging(Boolean originalPackage) {
        this.hasOriginalPackaging = originalPackage;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }
}
