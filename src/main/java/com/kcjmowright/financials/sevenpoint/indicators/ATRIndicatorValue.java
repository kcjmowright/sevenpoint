package com.kcjmowright.financials.sevenpoint.indicators;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ATRIndicatorValue {
  private LocalDateTime timestamp;
  private BigDecimal value;
  private BigDecimal average;
}
