package com.kcjmowright.financials.sevenpoint.indicators;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;
import static com.kcjmowright.financials.math.BigDecimalAverage.average;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.Getter;

/**
 * <a href="https://www.investopedia.com/terms/c/commoditychannelindex.asp">Commodity Channel Index</a>
 */
@Getter
public class CommodityChannelIndex {

  private final int period;
  private final BigDecimal coefficient;
  private final List<Quote> quotes;
  private final List<IndicatorValue> values;

  private static final BigDecimal THREE = new BigDecimal("3.0", MATH_CONTEXT);
  private static final BigDecimal DEFAULT_COEFFICIENT = new BigDecimal("0.15", MATH_CONTEXT);
  private static final int DEFAULT_PERIOD = 20;

  public CommodityChannelIndex(List<Quote> quotes, int period, BigDecimal coefficient) {
    this.quotes = quotes;
    this.period = period;
    this.coefficient = coefficient;
    this.values = new ArrayList<>();
    calculateAll();
  }

  public CommodityChannelIndex(List<Quote> quotes) {
    this(quotes, DEFAULT_PERIOD, DEFAULT_COEFFICIENT);
  }

  public void addQuote(Quote quote) {
    this.quotes.add(quote);
    if (this.period <= this.quotes.size()) {
      int idx = this.quotes.size();
      calculate(this.quotes.subList(idx - this.period, idx));
    }
  }

  void calculateAll() {
    if (this.period <= this.quotes.size()) {
      for (int idx = this.period; idx <= this.quotes.size(); idx++) {
        calculate(this.quotes.subList(idx - this.period, idx));
      }
    }
  }

  void calculate(List<Quote> slice) {
    List<BigDecimal> typicalPrices = slice.stream().map(this::calculateTypicalPrice).toList();
    BigDecimal movingAverage = average(typicalPrices);
    List<BigDecimal> absDeviations = typicalPrices.stream().map(tp -> tp.subtract(movingAverage, MATH_CONTEXT).abs()).toList();
    BigDecimal meanDeviation = average(absDeviations);
    BigDecimal cci = typicalPrices.getLast().subtract(movingAverage, MATH_CONTEXT).divide(coefficient.multiply(meanDeviation, MATH_CONTEXT), MATH_CONTEXT);
    IndicatorValue value = new IndicatorValue(slice.getLast().getTimestamp(), cci);
    values.add(value);
  }

  BigDecimal calculateTypicalPrice(Quote quote) {
    return quote.getHigh().add(quote.getLow()).add(quote.getClose()).divide(THREE, MATH_CONTEXT);
  }
}
