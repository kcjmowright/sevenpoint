package com.kcjmowright.financials.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class QuoteDTO {
  private Long id;
  private String ticker;
  private LocalDateTime timestamp;
  private BigDecimal high;
  private BigDecimal low;
  private BigDecimal open;
  private BigDecimal close;
}
