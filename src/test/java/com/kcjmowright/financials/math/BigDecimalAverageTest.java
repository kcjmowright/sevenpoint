package com.kcjmowright.financials.math;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static com.kcjmowright.financials.math.BigDecimalAverage.average;

import java.math.BigDecimal;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

public class BigDecimalAverageTest {

  @Test
  void testAverage() {
    var input = DoubleStream.of(1.2, 3.3, 4.45454545, 3.1415927, 2.0, 10.01).mapToObj(BigDecimal::valueOf).toList();
    var output = average(input);
    assertEquals(new BigDecimal("4.017689691666667"), output);
  }

  @Test
  void testAverageRequireNonNull() {
    var input = Stream.of(BigDecimal.valueOf(1.2), null).toList();
    assertThrows(NullPointerException.class, () -> average(input));
  }
}
