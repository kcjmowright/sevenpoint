package com.kcjmowright.financials.sevenpoint.indicators;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Gap {
  private int direction;
  private BigDecimal high;
  private BigDecimal low;
  private LocalDateTime timestamp;
}
