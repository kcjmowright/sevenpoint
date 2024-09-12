package com.kcjmowright.financials.sevenpoint.patterns;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

/**
 * An Abandoned Baby Bottom is a three candle reversal pattern at the end of a downtrend.
 * <p>
 * The first candle is a large red candle.
 * The second candle is a Doji below the close of the first candle.
 * The third candle is a huge green candle above the second candle.
 */
public class AbandonedBabyBottom extends Doji implements ICandlestickPattern {
  @Override
  public int getDirection() {
    return 1;
  }

  @Override
  public boolean analyze(List<Quote> quotes, BigDecimal slope) {
    if (slope.compareTo(BigDecimal.ZERO) > 0) { // sloping upward.
      return false;
    }
    int idx = quotes.size();
    final Quote third = quotes.get(--idx);
    final Quote second = quotes.get(--idx);
    final Quote first = quotes.get(--idx);
    if (!super.analyze(List.of(second), slope)) { // if second candle is not a doji
      return false;
    }
    if (first.getClose().compareTo(first.getOpen()) >= 0 // If the first candle is not red or
        || first.getClose().subtract(first.getOpen()).abs().multiply(BigDecimal.TWO, MATH_CONTEXT).compareTo(first.getHigh().subtract(first.getLow())) <= 0) { // not big
      return false;
    }
    return third.getClose().compareTo(second.getOpen().max(second.getClose())) < 0 // If the third candle is not green or
        && third.getOpen().subtract(third.getClose()).abs().multiply(BigDecimal.TWO, MATH_CONTEXT).compareTo(third.getHigh().subtract(third.getClose())) > 0;
  }
}
