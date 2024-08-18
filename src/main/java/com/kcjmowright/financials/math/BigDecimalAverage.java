package com.kcjmowright.financials.math;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;
import static com.kcjmowright.financials.math.BigDecimalSum.sum;

import java.math.BigDecimal;
import java.util.List;

public class BigDecimalAverage {
  public static BigDecimal average(List<BigDecimal> values) {
    return sum(values).divide(new BigDecimal(values.size()), MATH_CONTEXT);
  }
}
