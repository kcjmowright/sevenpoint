package com.kcjmowright.financials.sevenpoint.analysis;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.IndicatorValue;
import com.kcjmowright.financials.sevenpoint.indicators.MovingPriceAverage;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class MovingAverageEvidence implements Evidence {

  @Override
  public int process(List<Quote> quotes) {
    log.info("Processing moving average evidence");
    MovingPriceAverage mpa = new MovingPriceAverage(quotes);
    IndicatorValue avg = mpa.getValues().getLast();
    BigDecimal priceDivergence = quotes.getLast().getClose().subtract(avg.getValue());
    /*
    > 0 above average
    0 at avg
    < 0 below average
     */
    return switch (priceDivergence.compareTo(BigDecimal.ZERO)) {
      case -1 -> avg.getStdDev().negate().compareTo(priceDivergence);
      case 1 -> avg.getStdDev().compareTo(priceDivergence);
      default -> 0;
    };
  }
}
