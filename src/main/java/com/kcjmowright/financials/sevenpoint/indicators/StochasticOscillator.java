package com.kcjmowright.financials.sevenpoint.indicators;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;
import static com.kcjmowright.financials.math.BigDecimalAverage.average;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.AllArgsConstructor;
import lombok.Data;
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

  @Data
  @AllArgsConstructor
  public static class Value {
    LocalDateTime date;
    BigDecimal k;
    BigDecimal d;
  }

  /**
   * The calculated values.
   */
  private final List<Value> values = new ArrayList<>();
  private final List<Quote> quotes;
  private final int period;

  private static final BigDecimal ONE_HUNDRED = new BigDecimal("100.0", MATH_CONTEXT);
  private static final int DEFAULT_PERIOD = 14;

  public StochasticOscillator(List<Quote> quotes, int period) {
    this.quotes = quotes;
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

  static BigDecimal d(List<Value> values) {
    return average(values.subList(values.size() - 3, values.size()).stream().map(Value::getK).toList());
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
        Value value = new Value(quote.getTimestamp(), k(quote.getClose(), minMax[0], minMax[1]), null);

        this.values.add(value);
        if (this.values.size() >= 3) {
          value.d = d(values);
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
      Value value = new Value(quote.getTimestamp(), k(quote.getClose(), minMax[0], minMax[1]), null);

      this.values.add(value);
      if (this.values.size() >= 3) {
        value.d = d(this.values);
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
