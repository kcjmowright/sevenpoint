package com.kcjmowright.financials.sevenpoint.quotes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "quote")
public class Quote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "ticker", nullable = false)
  private String ticker;

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

  @Column(name = "adjclose")
  private BigDecimal adjClose;
}
