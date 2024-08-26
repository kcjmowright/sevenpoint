package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class Hammer implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return 1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    if (slope.compareTo(BigDecimal.ZERO) > 0) {
      return false;
    }
    return isPattern(quotes);
  }

  boolean isPattern(List<Quote> quotes) {
    final Quote quote = quotes.getLast();
    final BigDecimal bodySize = quote.getClose().subtract(quote.getOpen()).abs();
    final BigDecimal wickSize = quote.getHigh().subtract(quote.getClose().max(quote.getOpen()));
    final BigDecimal legSize = quote.getOpen().min(quote.getClose()).subtract(quote.getLow());
    return bodySize.multiply(BigDecimal.TWO).compareTo(legSize) <= 0
        && wickSize.compareTo(bodySize) < 0;
  }
}
