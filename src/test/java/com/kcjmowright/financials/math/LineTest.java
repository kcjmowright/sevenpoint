package com.kcjmowright.financials.math;

import static java.math.BigDecimal.valueOf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LineTest {

  @Test
  void shouldCalculateSlope() {
    var line1 = new Line(3, 1, 5, 6);
    assertEquals(valueOf(2.5), line1.slope());

    var line2 = new Line(3, 2, 5, 8);
    assertEquals(valueOf(3), line2.slope());
  }

  @Test
  void shouldThrowExceptionForDeltaXof0() {
    var line1 = new Line(3, 1, 3, 2);
    assertThrows(Exception.class, () -> line1.slope());
  }

  @Test
  void shouldCalculateTheYIntercept() {
    var line1 = new Line(3, 2, 5, 8);
    assertEquals(valueOf(-7.0), line1.getYIntercept());
  }

  @Test
  void shouldThrowExceptionForYInterceptIfSlopeIsUndefined() {
    var line1 = new Line(3, 1, 3, 2);
    assertThrows(Exception.class, () -> line1.getYIntercept());
  }

  @Test
  void shouldCalculateTheLength() {
    var line1 = new Line(3, 1, 5, 6);
    assertEquals(valueOf(5.385164807134504), line1.length());

    var line2 = new Line(3, 2, 5, 8);
    assertEquals(valueOf(6.324555320336759), line2.length());
  }

  @Test
  void shouldCompareEqualityAndParallelLines() {
    var line1 = new Line(3, 1, 5, 6);
    var line2 = new Line(3, 1, 5, 6);
    assertTrue(line1.isParallelTo(line2));
    assertTrue(line1.equals(line2));

    var line3 = new Line(4, 2, 6, 7);
    assertTrue(line1.isParallelTo(line3));
    assertFalse(line1.equals(line3));
  }
}
