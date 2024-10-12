package com.kcjmowright.financials.sevenpoint.indicators;

import static java.util.Objects.requireNonNull;

import static com.kcjmowright.financials.math.BigDecimalAverage.average;

import java.util.ArrayList;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.Getter;

@Getter
public class MovingAverage {
  public static final int DEFAULT_PERIOD = 20;

  private final List<Quote> quotes;

  private final List<IndicatorValue> values = new ArrayList<>();

  private final int period;

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   */
  public MovingAverage(List<Quote> quotes) {
    this(quotes, DEFAULT_PERIOD);
  }

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   * @param period the length of the period to consider for the average.  Default is DEFAULT_PERIOD.
   */
  public MovingAverage(List<Quote> quotes, int period) {
    this.quotes = requireNonNull(quotes, "Expected quotes");
    this.period = period;
    calculate();
  }

  void calculate() {
    if (quotes.isEmpty()) {
      return;
    }
    int len = quotes.size();
    for (int i = 0; i < len; i++) {
      final IndicatorValue iv = new IndicatorValue(quotes.get(i).getTimestamp(), null);
      values.add(iv);
      if (values.size() >= period) {
        iv.setValue(average(quotes.subList(i - period + 1, i + 1).stream().map(Quote::getClose).toList()));
      }
    }
  }
}
