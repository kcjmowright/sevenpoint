package com.kcjmowright.financials.sevenpoint.analysis;

import java.math.BigDecimal;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.IndicatorValue;
import com.kcjmowright.financials.sevenpoint.indicators.MovingVolumeAverage;
import com.kcjmowright.financials.sevenpoint.indicators.Trend;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Volume compared to the trend.
 */
@NoArgsConstructor
@Slf4j
public class VolumeEvidence implements Evidence {
  @Override
  public int process(List<Quote> quotes) {
    log.info("Processing volume evidence");
    Quote lastQuote = quotes.getLast();
    int shortTermVolumeTrend = Trend.findVolumeSlope(quotes, 5).compareTo(BigDecimal.ZERO);
    // int longTermVolumeTrend = Trend.findVolumeSlope(quotes, 20).compareTo(BigDecimal.ZERO);
    MovingVolumeAverage mva = new MovingVolumeAverage(quotes);
    IndicatorValue avg = mva.getValues().getLast();
    BigDecimal volumeAvgDivergence = BigDecimal.valueOf(lastQuote.getVolume()).subtract(avg.getValue());
    int evidence = 0;
    if (volumeAvgDivergence.compareTo(BigDecimal.ZERO) > 0 && shortTermVolumeTrend > 0) {
      evidence++;
    } else if (volumeAvgDivergence.compareTo(BigDecimal.ZERO) < 0 && shortTermVolumeTrend < 0) {
      evidence--;
    }
    return evidence;
  }
}
