package com.kcjmowright.financials.sevenpoint.math;

import static com.kcjmowright.financials.sevenpoint.config.MathConfig.MATH_CONTEXT;
import static com.kcjmowright.financials.sevenpoint.math.BigDecimalSum.sum;

import java.math.BigDecimal;
import java.util.List;

public class BigDecimalAverage {
  public static BigDecimal average(List<BigDecimal> values) {
    return sum(values).divide(new BigDecimal(values.size()), MATH_CONTEXT);
  }

  public static BigDecimal average(BigDecimal ...values) {
    return sum(values).divide(new BigDecimal(values.length), MATH_CONTEXT);
  }
}
