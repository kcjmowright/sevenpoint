package com.kcjmowright.financials.math;

import static java.math.BigDecimal.valueOf;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Point {
  private final BigDecimal x;
  private final BigDecimal y;

  public Point(BigDecimal x, BigDecimal y) {
    this.x = x;
    this.y = y;
  }

  public Point(double x, double y) {
    this.x = valueOf(x);
    this.y = valueOf(y);
  }
}
