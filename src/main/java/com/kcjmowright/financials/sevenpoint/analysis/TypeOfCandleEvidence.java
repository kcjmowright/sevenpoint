package com.kcjmowright.financials.sevenpoint.analysis;

import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.patterns.CandlestickPatterns;
import com.kcjmowright.financials.sevenpoint.patterns.ICandlestickPattern;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class TypeOfCandleEvidence implements Evidence {

  @Override
  public int process(List<Quote> quotes) {
    log.info("Processing candlestick evidence");
    CandlestickPatterns patterns = new CandlestickPatterns(quotes);
    CandlestickPatterns.Result result = patterns.analyze();
    log.info("2 period slope: {}, short period slope: {}, long period slope: {}, patterns: {}",
        result.twoPeriodSlope(), result.shortPeriodSlope(), result.longPeriodSlope(), result.patterns());
    return Integer.compare(result.patterns().stream().mapToInt(ICandlestickPattern::getDirection).sum(), 0);
  }
}
