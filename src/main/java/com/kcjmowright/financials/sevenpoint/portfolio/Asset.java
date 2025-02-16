package com.kcjmowright.financials.sevenpoint.portfolio;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "asset")
@NoArgsConstructor
@AllArgsConstructor
public class Asset {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "symbol", nullable = false)
  private String symbol;

  @Column(name = "bought")
  private LocalDateTime bought;

  @Column(name = "sold")
  private LocalDateTime sold;

  @Column(name="purchaseprice")
  private BigDecimal purchasePrice;

  @Column(name="sellprice")
  private BigDecimal sellPrice;

  @Column(name="quantity", nullable = false)
  private BigDecimal quantity;

  @Column(name="type", nullable = false)
  private AssetType type;

}
