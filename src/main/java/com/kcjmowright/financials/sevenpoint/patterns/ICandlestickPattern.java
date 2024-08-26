package com.kcjmowright.financials.sevenpoint.patterns;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public interface ICandlestickPattern {

  /**
   * Indicates whether this candle is bullish (>0), bearish (<0), or neutral (0).
   * @return an int value that indicates bearish, neutral, or bullish direction.
   */
  int getDirection();

  /**
   * Analyzes the given quotes for the short and long periods and indicates whether the quotes fit the pattern.
   * @param quotes a collection of quotes.
   * @param slope the trend or {@code Line} of the short period.
   * @return true if the pattern fits the quotes.
   */
  boolean analyze(List<Quote> quotes, BigDecimal slope);
}
