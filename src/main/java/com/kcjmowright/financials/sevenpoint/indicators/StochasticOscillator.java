package com.kcjmowright.financials.sevenpoint.indicators;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;
import static com.kcjmowright.financials.math.BigDecimalAverage.average;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.Getter;

/**
 * <code>k = (Most Recent Price - Period Low) / (Period High - Period Low) * 100.0</code>
 * <p>
 * Where:<ul>
 * <li>k = the current calculated value.
 * <li>d = 3 - period simple moving average of k.
 */
@Getter
public class StochasticOscillator {

  /**
   * The calculated values.
   */
  private final List<StochasticValue> stochasticValues = new ArrayList<>();
  private final List<Quote> quotes;
  private final int period;

  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100.0", MATH_CONTEXT);
  private static final int DEFAULT_PERIOD = 14;

  public StochasticOscillator(List<Quote> quotes, int period) {
    this.quotes = Objects.requireNonNull(quotes);
    this.period = period;
    calculate();
  }

  public StochasticOscillator(List<Quote> quotes) {
    this(quotes, DEFAULT_PERIOD);
  }

  public StochasticOscillator() {
    this(new ArrayList<>());
  }

  static BigDecimal k(BigDecimal close, BigDecimal min, BigDecimal max) {
    return close.subtract(min).divide(max.subtract(min), MATH_CONTEXT).multiply(ONE_HUNDRED, MATH_CONTEXT);
  }

  static BigDecimal d(List<StochasticValue> stochasticValues) {
    return average(stochasticValues.subList(stochasticValues.size() - 3, stochasticValues.size()).stream().map(StochasticValue::getK).toList());
  }

  /**
   * Calculates k and d values for `this.quotes`.
   */
  void calculate() {
    if (this.period <= this.quotes.size()) {
      for (int idx = this.period; idx <= this.quotes.size(); idx++) {
        List<Quote> slice = this.quotes.subList(idx - this.period, idx);
        BigDecimal[] minMax = this.calculateMinMax(slice);
        Quote quote = slice.getLast();
        StochasticValue stochasticValue = new StochasticValue(quote.getTimestamp(), k(quote.getClose(), minMax[0], minMax[1]), null);

        this.stochasticValues.add(stochasticValue);
        if (this.stochasticValues.size() >= 3) {
          stochasticValue.d = d(stochasticValues);
        }
      }
    }
  }

  /**
   * Modifies `this` object by calculating a new value for k and d and pushing the result onto `this.values`.
   *
   * @param quote a Quote object.
   */
  public void addQuote(Quote quote) {
    this.quotes.add(quote);
    if (this.period <= this.quotes.size()) {
      List<Quote> slice = this.quotes.subList(this.quotes.size() - this.period, this.quotes.size());
      BigDecimal[] minMax = calculateMinMax(slice);
      StochasticValue stochasticValue = new StochasticValue(quote.getTimestamp(), k(quote.getClose(), minMax[0], minMax[1]), null);

      this.stochasticValues.add(stochasticValue);
      if (this.stochasticValues.size() >= 3) {
        stochasticValue.d = d(this.stochasticValues);
      }
    }
  }

  /**
   * @param quotes a range of quotes.
   * @return BigDecimal[] the min and max of the given quotes.
   */
  private BigDecimal[] calculateMinMax(List<Quote> quotes) {
    BigDecimal max = null;
    BigDecimal min = null;
    for (Quote quote : quotes) {
      if (max == null || max.compareTo(quote.getHigh()) < 0) {
        max = quote.getHigh();
      }
      if (min == null || min.compareTo(quote.getLow()) > 0) {
        min = quote.getLow();
      }
    }
    return new BigDecimal[] {min, max};
  }
}
