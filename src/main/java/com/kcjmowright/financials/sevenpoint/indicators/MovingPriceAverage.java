package com.kcjmowright.financials.sevenpoint.indicators;

import static java.util.Objects.requireNonNull;

import static com.kcjmowright.financials.sevenpoint.math.BigDecimalAverage.average;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class MovingPriceAverage extends AbstractMovingAverage {

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   */
  public MovingPriceAverage(List<Quote> quotes) {
    super(quotes);
  }

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   * @param period the length of the period to consider for the average.  Default is DEFAULT_PERIOD.
   */
  public MovingPriceAverage(List<Quote> quotes, int period) {
    super(quotes, period);
  }

  Function<Quote, BigDecimal> extractData() {
    return Quote::getClose;
  }

}
