package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class HangingMan extends Hammer implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return -1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    if (slope.compareTo(BigDecimal.ZERO) < 0) {
      return false;
    }
    return isPattern(quotes);
  }
}
