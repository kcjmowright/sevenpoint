package com.kcjmowright.financials.math;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.kcjmowright.financials.math.Slope.slope;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

public class SlopeTest {

  @Test
  void testSlope() {
    BigDecimal actual = slope(new Point(3, 1), new Point(5, 6));
    assertEquals(BigDecimal.valueOf(2.5), actual);
    actual = slope(new Point(3, 2), new Point(5, 8));
    assertEquals(BigDecimal.valueOf(3), actual);
  }
}
