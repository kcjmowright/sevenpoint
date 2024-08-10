package com.kcjmowright.financials.math;

import static java.util.Objects.nonNull;

import static com.kcjmowright.financials.math.BigDecimalSum.sum;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class BigDecimalAverage {
    public static BigDecimal average(List<BigDecimal> values) {
        return sum(values).divide(new BigDecimal(values.size()), 8, RoundingMode.HALF_EVEN);
      }
}
