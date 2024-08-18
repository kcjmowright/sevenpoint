package com.kcjmowright.financials.sevenpoint.company;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Getter;
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
@Table(name = "quote")
public class Quote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "symbol", nullable = false)
  private String symbol;

  @Column(name = "mark", nullable = false)
  private LocalDateTime mark;

  @Column(name = "high")
  private BigDecimal high;

  @Column(name = "low")
  private BigDecimal low;

  @Column(name = "open")
  private BigDecimal open;

  @Column(name = "close")
  private BigDecimal close;

  @Column(name = "volume")
  private Long volume;

  @Column(name = "adjclose")
  private BigDecimal adjClose;

  public String toString() {
    return "Quote {id=%s, symbol=%s, mark=%s, high=%s, low=%s, open=%s, close=%s, volume=%s, adjClose=%s}"
        .formatted(id, symbol, mark, high, low, open, close, volume, adjClose);
  }
}
