package com.kcjmowright.financials.math;

import static java.util.Objects.requireNonNull;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;

import java.math.BigDecimal;

public class Slope {

  /**
   *
   * @param rise the length of the rise, height, or Y axis.
   * @param run the length of the run, width, or X axis.
   * @return the slope
   */
  public static BigDecimal slope(BigDecimal rise, BigDecimal run) {
    requireNonNull(rise, "Missing rise argument");
    requireNonNull(run, "Missing run argument");
    return rise.divide(run, MATH_CONTEXT);
  }

  public static BigDecimal slope(double rise, double run) {
    return slope(BigDecimal.valueOf(rise), BigDecimal.valueOf(run));
  }

  public static BigDecimal slope(Point a, Point b) {
    requireNonNull(a, "Missing point a argument");
    requireNonNull(b, "Missing point b argument");
    return slope(b.getY().subtract(a.getY()), b.getX().subtract(a.getX()));
  }
}
