package com.recharged.backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StripePriceObject {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private String id;
  private String currency;
  private Long unitAmount;
  private String stripePriceId;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

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
