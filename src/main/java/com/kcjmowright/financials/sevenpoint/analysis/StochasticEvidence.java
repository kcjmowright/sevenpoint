package com.kcjmowright.financials.sevenpoint.analysis;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

import com.kcjmowright.financials.sevenpoint.math.LinearLeastSquares;
import com.kcjmowright.financials.sevenpoint.math.Point;
import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.StochasticOscillator;
import com.kcjmowright.financials.sevenpoint.indicators.StochasticValue;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class StochasticEvidence implements Evidence {
  @Override
  public int process(List<Quote> quotes) {
    log.info("Processing stochastic oscillator evidence");
    int shortPeriod = 5;

    StochasticOscillator oscillator = new StochasticOscillator(quotes);
    List<StochasticValue> values =
        oscillator.getStochasticValues().subList(oscillator.getStochasticValues().size() - shortPeriod, oscillator.getStochasticValues().size());
    BigDecimal lastStochastic = values.getLast().getK();
    List<Quote> quotesSublist = quotes.subList(quotes.size() - shortPeriod, quotes.size());
    List<Point> quotesPoints = IntStream.range(0, shortPeriod).mapToObj(i -> new Point(BigDecimal.valueOf(i), quotesSublist.get(i).getClose())).toList();
    BigDecimal quotesSlope = LinearLeastSquares.linearLeastSquares(quotesPoints).line().slope();
    List<Point> stochasticPoints = IntStream.range(0, shortPeriod).mapToObj(i -> new Point(BigDecimal.valueOf(i), values.get(i).getK())).toList();
    BigDecimal stochasticSlope = LinearLeastSquares.linearLeastSquares(stochasticPoints).line().slope();
    log.info("Stochastic slope: {}, Quotes slope: {}", stochasticSlope, quotesSlope);
    if (quotesSlope.compareTo(BigDecimal.ZERO) < 0 && stochasticSlope.compareTo(BigDecimal.ZERO) > 0) {
      log.info("Stochastic positive divergence");
      return 1;
    }
    if (quotesSlope.compareTo(BigDecimal.ZERO) > 0 && stochasticSlope.compareTo(BigDecimal.ZERO) < 0) {
      log.info("Stochastic negative divergence");
      return -1;
    }
    if (lastStochastic.compareTo(BigDecimal.valueOf(80)) > 0) {
      log.info("Stochastic is overbought");
      return -1;
    }
    if (lastStochastic.compareTo(BigDecimal.valueOf(20)) < 0) {
      log.info("Stochastic is oversold");
      return 1;
    }
    return stochasticSlope.compareTo(BigDecimal.ZERO);
  }
}
