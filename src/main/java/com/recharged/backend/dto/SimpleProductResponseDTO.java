package com.recharged.backend.dto;

public class SimpleProductResponseDTO {
    private Long id;
    private String name;
    private String vendor;
    private Float price;
    private Float promotionPrice;
    private String blurb;
    private String productImage;

    public SimpleProductResponseDTO() {
        this.id = id;
        this.name = name;
        this.productImage = productImage;
        this.promotionPrice = promotionPrice;
        this.blurb = blurb;
        this.vendor = vendor;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public Float getPromotionPrice() {
        return promotionPrice;
    }

    public void setPromotionPrice(Float promotionPrice) {
        this.promotionPrice = promotionPrice;
    }
}