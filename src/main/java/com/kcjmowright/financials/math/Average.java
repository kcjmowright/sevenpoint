package com.kcjmowright.financials.math;

import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;

public class Average {
  public static double average(double[] values) {
    return DoubleStream.of(values).average().getAsDouble();
  }

  public static double average(List<Double> values) {
    return values.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).average().getAsDouble();
  }
}
