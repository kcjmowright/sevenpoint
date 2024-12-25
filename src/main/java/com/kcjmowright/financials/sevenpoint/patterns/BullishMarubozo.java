package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public class BullishMarubozo extends Marubozo {
  @Override
  public int getDirection() {
    return 1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    return super.analyze(quotes, slope) && quotes.getLast().getOpen().compareTo(quotes.getLast().getClose()) < 0;
  }
}
