package com.kcjmowright.financials.sevenpoint.indicators;

import static java.util.Objects.requireNonNull;

import static com.kcjmowright.financials.sevenpoint.config.MathConfig.MATH_CONTEXT;
import static com.kcjmowright.financials.sevenpoint.math.BigDecimalAverage.average;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.Getter;

@Getter
public abstract class AbstractMovingAverage {
  public static final int DEFAULT_PERIOD = 20;
  private final List<Quote> quotes;
  private final List<IndicatorValue> values = new ArrayList<>();
  private final int period;

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   */
  public AbstractMovingAverage(List<Quote> quotes) {
    this(quotes, DEFAULT_PERIOD);
  }

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   * @param period the length of the period to consider for the average.  Default is DEFAULT_PERIOD.
   */
  public AbstractMovingAverage(List<Quote> quotes, int period) {
    this.quotes = requireNonNull(quotes, "Expected quotes");
    this.period = period;
    calculate();
  }

  abstract Function<Quote, BigDecimal> extractData();

  void calculate() {
    if (quotes.isEmpty()) {
      return;
    }
    int len = quotes.size();
    for (int i = 0; i < len; i++) {
      final IndicatorValue iv = new IndicatorValue(quotes.get(i).getTimestamp(), null, null);
      values.add(iv);
      if (values.size() >= period) {
        List<Quote> sublist = quotes.subList(i - period + 1, i + 1);
        BigDecimal avg = average(sublist.stream().map(extractData()).toList());
        iv.setValue(avg);
        BigDecimal stdDev = average(sublist.stream().map(extractData()).map(v -> v.subtract(avg).pow(2)).toList()).sqrt(MATH_CONTEXT);
        iv.setStdDev(stdDev);
      }
    }
  }
}
