package com.kcjmowright.financials.math;

import static java.math.BigDecimal.valueOf;
import static java.util.Objects.isNull;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class Line {
  private final Point start;
  private final Point end;

  public Line(Point start, Point end) {
    this.start = start;
    this.end = end;
  }

  public Line(BigDecimal x1, BigDecimal y1, BigDecimal x2, BigDecimal y2) {
    this(new Point(x1, y1), new Point(x2, y2));
  }

  public Line(double x1, double y1, double x2, double y2) {
    this(valueOf(x1), valueOf(y1), valueOf(x2), valueOf(y2));
  }

  /**
   * Calculates the slope of this line.
   * (y2 - y1) / (x2 - x1);
   * @return the slope value.
   */
  public BigDecimal slope() {
    return Slope.slope(start, end);
  }

  /**
   * Calculates the length (distance) of this line.
   * sqrt( (x2 - x1)^2 + (y2 - y1)^2 )
   * @return the length of this line.
   */
  public BigDecimal length() {
    return end.getX().subtract(start.getX()).pow(2).add(end.getY().subtract(start.getY()).pow(2)).sqrt(MATH_CONTEXT);
  }

  /**
   * Calculates the Y intercept of this line.
   * @return the Y intercept.
   */
  public BigDecimal getYIntercept() {
    return start.getY().subtract(slope().multiply(start.getX(), MATH_CONTEXT));
  }

  /**
   * Compares this line to the given line and determines if the have the same slope.
   * @param line the other line.
   * @return true if the lines are parallel.
   */
  public boolean isParallelTo(Line line) {
    if (isNull(line)) {
      throw new IllegalArgumentException("Expected a valid line");
    }
    return slope().equals(line.slope());
  }
}
