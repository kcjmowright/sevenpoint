package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class AbandonedBabyBottom extends Doji implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return 1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    if (slope.compareTo(BigDecimal.ZERO) > 0) {
      return false;
    }
    int idx = quotes.size();
    final Quote last = quotes.get(--idx);
    final Quote nextToLast = quotes.get(--idx);
    if (nextToLast.getClose().compareTo(last.getOpen()) >= 0) {
      return false;
    }
    return super.analyze(List.of(nextToLast), slope);
  }
}
