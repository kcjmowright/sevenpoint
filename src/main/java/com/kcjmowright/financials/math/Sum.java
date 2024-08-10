package com.kcjmowright.financials.math;

import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;

public class Sum {

  public static double sum(List<Double> values) {
    return values.stream().filter(Objects::nonNull).mapToDouble(Double::doubleValue).reduce(Double::sum).getAsDouble();
  }

  public static double sum(double[] values) {
    return DoubleStream.of(values).reduce(Double::sum).getAsDouble();
  }

  public static double sum(double value) {
    return value;
  }

}
