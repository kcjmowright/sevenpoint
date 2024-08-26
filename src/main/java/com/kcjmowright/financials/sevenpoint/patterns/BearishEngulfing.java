package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class BearishEngulfing implements  ICandlestickPattern {
  @Override
  public int getDirection() {
    return -1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    if (slope.compareTo(BigDecimal.ZERO) < 0) { // If slope is descending, this is not in an uptrend.
      return false;
    }
    int idx = quotes.size();
    final Quote last = quotes.get(--idx);
    final Quote nextToLast = quotes.get(--idx);
    return last.getClose().compareTo(nextToLast.getOpen()) <= 0 && last.getOpen().compareTo(nextToLast.getClose()) > 0;
  }
}
