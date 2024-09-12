package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

/**
 * A Doji is when the opening and close are nearly identical.
 *
 * <pre>
 * (O == C) or (20 * ABS(O - C) <= ABS(H - L))
 *
 * O = Open
 * C = Close
 * H = High
 * L = Low
 * </pre>
 */
public class Doji implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return 0;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    final Quote quote = quotes.getLast();
    return quote.getOpen().equals(quote.getClose())
        || new BigDecimal(20).multiply(quote.getOpen().subtract(quote.getClose()).abs()).compareTo(quote.getHigh().subtract(quote.getLow()).abs()) <= 0;
  }
}
