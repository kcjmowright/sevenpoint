package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class InvertedHammer implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return 1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    if (slope.compareTo(BigDecimal.ZERO) > 0) { // if trend is bullish, then not an inverted hammer.
      return false;
    }
    final Quote quote = quotes.getLast();
    final BigDecimal bodySize = quote.getClose().subtract(quote.getOpen()).abs();
    final BigDecimal wickSize = quote.getHigh().subtract(quote.getClose().max(quote.getOpen()));
    final BigDecimal legSize = quote.getClose().min(quote.getOpen()).subtract(quote.getLow());
    return bodySize.compareTo(legSize) >= 0 && wickSize.compareTo(bodySize) > 0;
  }

}
