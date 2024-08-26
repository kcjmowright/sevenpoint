package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class StaleGreenLight implements ICandlestickPattern{
  @Override
  public int getDirection() {
    return -1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    int idx = quotes.size();
    final int expectedLightsCount = (int) (Math.log(idx) / Math.log(2));
    final int stop = idx - expectedLightsCount;
    for (; --idx >= stop;) {
      Quote quote = quotes.get(idx);
      if (quote.getOpen().compareTo(quote.getClose()) > 0) {
        return false;
      }
    }
    return true;
  }
}
