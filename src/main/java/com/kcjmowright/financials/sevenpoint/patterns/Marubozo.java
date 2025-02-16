package com.kcjmowright.financials.sevenpoint.patterns;

import static com.kcjmowright.financials.sevenpoint.config.MathConfig.MATH_CONTEXT;
import static com.kcjmowright.financials.sevenpoint.math.BigDecimalAverage.average;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

/**
 * A Marubozu Candle is a Long Candle which is all body, having no shadows / wicks.
 * <p>
 * <a href="https://www.investopedia.com/terms/m/marubozo.asp#:~:text=A%20Marubozo%20is%20a%20long,are%20used%20by%20technical%20traders.">Marubuzo: What it
 *  Means, How it Works, Why it's Used</a>
 */
public abstract class Marubozo implements ICandlestickPattern {

  private static final int DEFAULT_AVG_NUM_DAYS = 15;

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    final int len = quotes.size();
    final Quote last = quotes.getLast();
    final BigDecimal hl = last.getHigh().subtract(last.getLow()).abs();
    final BigDecimal oc = last.getOpen().subtract(last.getClose()).abs();
    final List<Quote> sublist = len <= DEFAULT_AVG_NUM_DAYS ? quotes : quotes.subList(len - DEFAULT_AVG_NUM_DAYS, len);
    final BigDecimal avg = average(sublist.stream().map(q -> q.getOpen().subtract(q.getClose()).abs()).toList());
    return hl.equals(oc) && hl.compareTo(BigDecimal.valueOf(3).multiply(avg).divide(BigDecimal.TWO, MATH_CONTEXT)) > 0;
  }
}
