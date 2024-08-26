package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

/**
 * A potential reversal in a downtrend indicated by a small increase in price
 * that can be contained within the given downward candle from the past day.
 * <a href="https://www.investopedia.com/terms/b/bullishharami.asp">Bullish Harami: Definition in Trading and Other Patterns</a>
 */
public class BullishHarami implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return 1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    if (slope.compareTo(BigDecimal.ZERO) >= 0) { // if in an uptrend then, this is not a bullish harami.
      return false;
    }
    int idx = quotes.size();
    final Quote last = quotes.get(--idx);
    final Quote nextToLast = quotes.get(--idx);
    if (nextToLast.getClose().compareTo(nextToLast.getOpen()) >= 0 // not a bearish candle
        || last.getClose().compareTo(last.getOpen()) < 0) { // a bearish candle
      return false;
    }
    final BigDecimal nextToLastLength = nextToLast.getOpen().subtract(nextToLast.getClose());
    final BigDecimal lastLength = last.getClose().subtract(last.getOpen());
    return nextToLastLength.compareTo(lastLength) > 0 // next to last is bigger than last
      && last.getOpen().compareTo(nextToLast.getClose()) > 0 // last open is above next to last close
      && last.getClose().compareTo(nextToLast.getOpen()) < 0; // last close is below next to last open.
  }
}
