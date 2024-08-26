package com.kcjmowright.financials.sevenpoint.patterns;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class ShootingStar implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return -1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    final Quote quote = quotes.getLast();
    final BigDecimal bodySize = quote.getClose().subtract(quote.getOpen()).abs();
    final BigDecimal wickSize = quote.getHigh().subtract(quote.getClose().max(quote.getOpen()));
    final BigDecimal legSize = quote.getClose().min(quote.getOpen()).subtract(quote.getLow());
    return bodySize.multiply(BigDecimal.TWO).compareTo(legSize) >= 0
        && wickSize.compareTo(legSize.divide(BigDecimal.TWO, MATH_CONTEXT)) <= 0;
  }
}
