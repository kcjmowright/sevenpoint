package com.kcjmowright.financials.sevenpoint.indicators;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class MovingVolumeAverage extends AbstractMovingAverage {

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   */
  public MovingVolumeAverage(List<Quote> quotes) {
    this(quotes, DEFAULT_PERIOD);
  }

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   * @param period the length of the period to consider for the average.  Default is DEFAULT_PERIOD.
   */
  public MovingVolumeAverage(List<Quote> quotes, int period) {
    super(quotes, period);
  }

  Function<Quote, BigDecimal> extractData() {
    return q -> BigDecimal.valueOf(q.getVolume());
  }

}
