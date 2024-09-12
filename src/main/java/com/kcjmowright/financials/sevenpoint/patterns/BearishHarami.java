package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

/**
 *
 */
public class BearishHarami implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return -1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    if (slope.compareTo(BigDecimal.ZERO) <= 0) { // if in a downtrend then, this is not a bearish harami.
      return false;
    }
    int idx = quotes.size();
    final Quote last = quotes.get(--idx);
    final Quote nextToLast = quotes.get(--idx);
    if (nextToLast.getClose().compareTo(nextToLast.getOpen()) <= 0 // a bearish candle
        || last.getClose().compareTo(last.getOpen()) > 0) { // a bullish candle
      return false;
    }
    final BigDecimal nextToLastLength = nextToLast.getClose().subtract(nextToLast.getOpen());
    final BigDecimal lastLength = last.getOpen().subtract(last.getClose());
    return nextToLastLength.compareTo(lastLength) > 0 // next to last is bigger than last
        && last.getOpen().compareTo(nextToLast.getClose()) < 0 // last open is below next to last close
        && last.getClose().compareTo(nextToLast.getOpen()) > 0; // last close is above next to last open.
  }
}
