package com.kcjmowright.financials.sevenpoint.indicators;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AverageIndicatorValue extends IndicatorValue {
  BigDecimal average;

  public AverageIndicatorValue(){
    super();
  }

  public AverageIndicatorValue(LocalDateTime timestamp, BigDecimal value, BigDecimal average) {
    super(timestamp, value);
    this.average = average;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    AverageIndicatorValue that = (AverageIndicatorValue) o;
    return Objects.equals(super.timestamp, that.timestamp) && Objects.equals(super.value, that.value) && Objects.equals(average, that.average);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), average);
  }

  @Override
  public String toString() {
    return "AverageIndicatorValue{" + "average=" + average + ", timestamp=" + timestamp + ", value=" + value + '}';
  }
}
