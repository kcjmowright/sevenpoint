package com.kcjmowright.financials.math;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class BigDecimalSum {
  public static BigDecimal sum(List<BigDecimal> values) {
    return values.stream().map(Objects::requireNonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
  }

  public static BigDecimal sumOfSquares(List<BigDecimal> values) {
    return values.stream().map(Objects::requireNonNull).map(v -> v.pow(2, MATH_CONTEXT)).reduce(BigDecimal.ZERO, BigDecimal::add);
  }
}
