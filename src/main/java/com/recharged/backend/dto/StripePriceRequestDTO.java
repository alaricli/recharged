package com.recharged.backend.dto;

public class StripePriceRequestDTO {
  private String currency;
  private Long unitAmount;
  private String stripePriceId;

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public String getStripePriceId() {
    return stripePriceId;
  }

  public void setStripePriceId(String stripePriceId) {
    this.stripePriceId = stripePriceId;
  }

  public Long getUnitAmount() {
    return unitAmount;
  }

  public void setUnitAmount(Long unitAmount) {
    this.unitAmount = unitAmount;
  }
}
