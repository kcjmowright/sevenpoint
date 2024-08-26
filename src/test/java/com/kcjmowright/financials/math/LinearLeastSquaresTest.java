package com.kcjmowright.financials.math;

import static java.math.BigDecimal.valueOf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.kcjmowright.financials.math.LinearLeastSquares.linearLeastSquares;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LinearLeastSquaresTest {

  @Test
  void givenEmptyInputThenThrowIllegalArgumentException() {

  }

  @Test
  void givenTwoUnequalSizedListsThenThrowIllegalArgumentException() {

  }

  @Test
  void givenTwoListsOfNumbersThenFindTheBestFittingLine() {
    var x = List.of(valueOf(1), valueOf(2), valueOf(3), valueOf(4), valueOf(5), valueOf(6), valueOf(7), valueOf(8), valueOf(9), valueOf(10), valueOf(11));
    var y =
        List.of(valueOf(23), valueOf(15), valueOf(20), valueOf(32), valueOf(17), valueOf(33), valueOf(21), valueOf(41), valueOf(20), valueOf(23), valueOf(46));
    var result = linearLeastSquares(x, y);

    assertEquals(new BigDecimal("17.236363636363634"), result.line().getYIntercept());
    assertEquals(new BigDecimal("1.536363636363636"), result.line().slope());
    assertEquals(new BigDecimal("0.2533800567778567"), result.coefficientOfDeterminationR2());
    assertEquals(new BigDecimal("9.220037467878908"), result.stdErrorOfEstimate());
  }

  @Test
  void givenAListOfPointsThenFindTheBestFittingLine() {
    var points = List.of(new Point(1, 23), new Point(2, 15), new Point(3, 20), new Point(4, 32), new Point(5, 17), new Point(6, 33), new Point(7, 21),
        new Point(8, 41), new Point(9, 20), new Point(10, 23), new Point(11, 46));
    var result = linearLeastSquares(points);

    assertEquals(new BigDecimal("17.2363636363636340"), result.line().getYIntercept());
    assertEquals(new BigDecimal("1.536363636363636"), result.line().slope());
    assertEquals(new BigDecimal("0.2533800567778567"), result.coefficientOfDeterminationR2());
    assertEquals(new BigDecimal("9.220037467878908"), result.stdErrorOfEstimate());
  }
}
