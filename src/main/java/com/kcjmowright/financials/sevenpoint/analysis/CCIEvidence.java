package com.kcjmowright.financials.sevenpoint.analysis;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import com.kcjmowright.financials.math.LinearLeastSquares;
import com.kcjmowright.financials.math.Point;
import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.CommodityChannelIndex;
import com.kcjmowright.financials.sevenpoint.indicators.IndicatorValue;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
* Overbought or oversold.
 * Buy or sell signal
 * Cross above or below the zero line.
 */
@NoArgsConstructor
@Slf4j
public class CCIEvidence implements Evidence {
  @Override
  public int process(List<Quote> quotes) {
    log.info("Processing CCI evidence");
    int shortPeriod = 5;

    CommodityChannelIndex cci = new CommodityChannelIndex(quotes);
    List<IndicatorValue> values = cci.getValues().subList(cci.getValues().size() - shortPeriod, cci.getValues().size());
    BigDecimal lastCciValue = values.getLast().getValue();
    BigDecimal nextToLastCciValue = values.get(values.size() - 2).getValue();
    if (nextToLastCciValue.compareTo(BigDecimal.ZERO) < 0 && lastCciValue.compareTo(BigDecimal.ZERO) > 0) {
      log.info("CCI crossed the 0 line to the upside");
      return 1;
    }
    if (nextToLastCciValue.compareTo(BigDecimal.ZERO) > 0 && lastCciValue.compareTo(BigDecimal.ZERO) < 0) {
      log.info("CCI crossed the 0 line to the downside");
      return -1;
    }

    List<Quote> quotesSublist = quotes.subList(quotes.size() - shortPeriod, quotes.size());
    List<Point> quotesPoints = IntStream.range(0, shortPeriod).mapToObj(i -> new Point(BigDecimal.valueOf(i), quotesSublist.get(i).getClose())).toList();
    BigDecimal quotesSlope = LinearLeastSquares.linearLeastSquares(quotesPoints).line().slope();
    List<Point> cciPoints = IntStream.range(0, shortPeriod).mapToObj(i -> new Point(BigDecimal.valueOf(i), values.get(i).getValue())).toList();
    BigDecimal cciSlope = LinearLeastSquares.linearLeastSquares(cciPoints).line().slope();
    log.info("CCI slope: {}, Quotes slope: {}", cciSlope, quotesSlope);
    if (quotesSlope.compareTo(BigDecimal.ZERO) < 0 && cciSlope.compareTo(BigDecimal.ZERO) > 0) {
      log.info("CCI positive divergence");
      return 1;
    }
    if (quotesSlope.compareTo(BigDecimal.ZERO) > 0 && cciSlope.compareTo(BigDecimal.ZERO) < 0) {
      log.info("CCI negative divergence");
      return -1;
    }
    List<BigDecimal> v = cci.getValues().stream().map(IndicatorValue::getValue).toList();
    BigDecimal min = Collections.min(v);
    BigDecimal max = Collections.max(v);
    BigDecimal threshold = min.abs().add(max).multiply(new BigDecimal("0.2"), MATH_CONTEXT);
    BigDecimal overboughtThreshold = max.subtract(threshold, MATH_CONTEXT);
    if (lastCciValue.compareTo(overboughtThreshold) > 0) {
      log.info("CCI is overbought");
      return -1;
    }
    BigDecimal oversoldThreshold = min.add(threshold, MATH_CONTEXT);
    if (lastCciValue.compareTo(oversoldThreshold) < 0) {
      log.info("CCI is oversold");
      return 1;
    }
    return cciSlope.compareTo(BigDecimal.ZERO);
  }
}
