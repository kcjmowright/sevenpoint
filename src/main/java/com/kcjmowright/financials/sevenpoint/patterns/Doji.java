package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class Doji implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return 0;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    final Quote quote = quotes.getLast();
    final BigDecimal bodyLength = quote.getOpen().subtract(quote.getClose()).abs();
    final BigDecimal wickLength = quote.getHigh().subtract(quote.getOpen().max(quote.getClose()));
    final BigDecimal legLength = quote.getOpen().min(quote.getClose()).subtract(quote.getLow());
    return wickLength.min(legLength).compareTo(bodyLength) > 0;
  }
}
