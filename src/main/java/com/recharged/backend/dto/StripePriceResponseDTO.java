package com.recharged.backend.dto;

public class StripePriceResponseDTO {
  private Long id;
  private String currency;
  private Long unitAmount;
  private String stripePriceId;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public Long getUnitAmount() {
    return unitAmount;
  }

  public void setUnitAmount(Long unitAmount) {
    this.unitAmount = unitAmount;
  }

  public String getStripePriceId() {
    return stripePriceId;
  }

  public void setStripePriceId(String stripePriceId) {
    this.stripePriceId = stripePriceId;
  }

}
