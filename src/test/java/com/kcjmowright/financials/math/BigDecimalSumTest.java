package com.kcjmowright.financials.math;

import static java.math.BigDecimal.valueOf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

public class BigDecimalSumTest {

  @Test
  void testSum() {
    BigDecimal actual = BigDecimalSum.sum(List.of(valueOf(1.01), valueOf(2.056), valueOf(1.31), valueOf(3.1415927)));
    assertEquals(valueOf(7.5175927), actual);
  }
}
