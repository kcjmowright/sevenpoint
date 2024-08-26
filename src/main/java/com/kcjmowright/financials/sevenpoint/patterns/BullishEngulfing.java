package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

/**
 * Appears at the end of a downtrend,
 * where the last quote open <= the next to last close
 * and the last quote close > the next to last open.
 */
public class BullishEngulfing implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return 1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    if (slope.compareTo(BigDecimal.ZERO) > 0) { // If slope is rising, this is not a downtrend.
      return false;
    }
    int idx = quotes.size();
    final Quote last = quotes.get(--idx);
    final Quote nextToLast = quotes.get(--idx);
    return last.getOpen().compareTo(nextToLast.getClose()) <= 0 && last.getClose().compareTo(nextToLast.getOpen()) > 0;
  }
}
