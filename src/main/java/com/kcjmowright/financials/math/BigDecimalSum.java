package com.kcjmowright.financials.math;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class BigDecimalSum {
  public static BigDecimal sum(List<BigDecimal> values) {
    return values.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
  } 
}
