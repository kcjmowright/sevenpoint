package com.kcjmowright.financials.sevenpoint.company;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
  @Id
  @Column(name = "symbol", nullable = false)
  private String symbol;

  @Column(name = "description")
  private String description;

  @Column(name = "name")
  private String name;

  @Column(name = "sector")
  private String sector;

  @Column(name = "industry")
  private String industry;

  @Column(name = "exchange")
  private String exchange;

  public String toString() {
    return "Company { symbol=%s, name=%s, description=%s, sector=%s, industry=%s, exchange=%s}".formatted(symbol, name, description, sector, industry,
        exchange);
  }
}
