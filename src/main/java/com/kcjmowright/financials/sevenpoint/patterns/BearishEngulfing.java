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
    int idx = quotes.size();
    final Quote last = quotes.get(--idx);
    final Quote nextToLast = quotes.get(--idx);
    return last.getOpen().compareTo(nextToLast.getClose().max(nextToLast.getOpen())) > 0
        && last.getClose().compareTo(nextToLast.getOpen().min(nextToLast.getClose())) < 0;
  }
}
