package com.kcjmowright.financials.math;

import static org.springframework.util.CollectionUtils.isEmpty;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;
import static com.kcjmowright.financials.math.BigDecimalAverage.average;
import static com.kcjmowright.financials.math.BigDecimalSum.sum;
import static com.kcjmowright.financials.math.BigDecimalSum.sumOfSquares;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import lombok.Data;

public class LinearLeastSquares {

  @Data
  static class Result {
    private final BigDecimal coefficientOfDeterminationR2;
    private final BigDecimal correlationR;
    private final Line line;
    private final BigDecimal sse;
    private final BigDecimal ssr;
    private final BigDecimal sst;
    private final BigDecimal stdErrorOfEstimate;
    private final BigDecimal sumX;
    private final BigDecimal sumY;
  }

  /**
   * Linear Regression
   *
   * @see http://mathworld.wolfram.com/LeastSquaresFitting.html
   * @param points the x & y values.
   * @return the {@code Result} which includes the regression line and the meta data of the calculation.
   */
  public static Result linearLeastSquares(List<Point> points) {
    if (isEmpty(points)) {
      throw new IllegalArgumentException("X & Y values are required");
    }
    return linearLeastSquares(points.stream().map(Point::getX).toList(), points.stream().map(Point::getY).toList());
  }

  /**
   * Linear Regression
   *
   * @see http://mathworld.wolfram.com/LeastSquaresFitting.html
   * @param valuesX x values
   * @param valuesY y values
   * @return the {@code Result} which includes the regression line and the meta data of the calculation.
   */
  public static Result linearLeastSquares(List<BigDecimal> valuesX, List<BigDecimal> valuesY) {
    if (isEmpty(valuesX) || isEmpty(valuesY)) {
      throw new IllegalArgumentException("X & Y values are required");
    }
    if (valuesX.size() != valuesY.size()) {
      throw new IllegalArgumentException("X & Y values are not of same size");
    }
    final BigDecimal count = BigDecimal.valueOf(valuesX.size());
    final BigDecimal x0 = valuesX.stream().min(BigDecimal::compareTo).orElseThrow();
    final BigDecimal xL = valuesX.stream().max(BigDecimal::compareTo).orElseThrow();
    final BigDecimal sumX = sum(valuesX);
    final BigDecimal sumY = sum(valuesY);
    final BigDecimal sumXX = sumOfSquares(valuesX);
    final BigDecimal sumYY = sumOfSquares(valuesY);
    final BigDecimal sumXY = sum(IntStream.range(0, valuesX.size()).mapToObj(i -> valuesX.get(i).multiply(valuesY.get(i))).toList());
    final BigDecimal avgX = average(valuesX);
    final BigDecimal avgY = average(valuesY);
    final BigDecimal xx = count.multiply(sumXX).subtract(sumX.pow(2));
    final BigDecimal yy = count.multiply(sumYY).subtract(sumY.pow(2));
    final BigDecimal xy = count.multiply(sumXY).subtract(sumX.multiply(sumY));
    final BigDecimal slope = Slope.slope(xy, xx);
    final BigDecimal yIntercept = avgY.subtract(slope.multiply(avgX));
    final BigDecimal correlationR = xy.divide(xx.multiply(yy).sqrt(MATH_CONTEXT), MATH_CONTEXT);
    final BigDecimal coefficientsOfDeterminationR2 = correlationR.pow(2, MATH_CONTEXT);
    final BigDecimal sst = sum(IntStream.range(0, valuesY.size()).mapToObj(i -> valuesY.get(i).subtract(avgY).pow(2)).toList());
    final BigDecimal sse = sst.subtract(coefficientsOfDeterminationR2.multiply(sst, MATH_CONTEXT));
    final BigDecimal stdErrorOfEstimate = sse.divide(count.subtract(BigDecimal.TWO), MATH_CONTEXT).sqrt(MATH_CONTEXT);
    final BigDecimal ssr = sst.subtract(sse);
    final Point start = new Point(x0, slope.multiply(x0).add(yIntercept));
    final Point end = new Point(xL, slope.multiply(xL).add(yIntercept));
    final Line line = new Line(start, end);

    return new Result(coefficientsOfDeterminationR2, correlationR, line, sse, ssr, sst, stdErrorOfEstimate, sumX, sumY);
  }
}
