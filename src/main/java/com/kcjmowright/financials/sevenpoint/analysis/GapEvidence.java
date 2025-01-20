package com.kcjmowright.financials.sevenpoint.analysis;

import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.PriceGap;
import com.kcjmowright.financials.sevenpoint.indicators.PriceGaps;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class GapEvidence implements Evidence {

  @Override
  public int process(List<Quote> quotes) {
    log.info("Processing gap evidence");
    PriceGaps pg = new PriceGaps(quotes);
    List<PriceGap> gaps = pg.getOpenedPriceGaps();
    Quote lastQuote = quotes.getLast();
    if (gaps.isEmpty()) {
      log.info("No gaps");
      return 0;
    }
    return gaps.stream().map(g -> switch (g.getHigh().compareTo(lastQuote.getClose())) {
      // gap high is greater than last close
      case 1 -> g.getLow().compareTo(lastQuote.getClose());
      // gap high is less than last close
      case -1 -> -1;
      default -> 0;
    }).reduce(0, Integer::sum).compareTo(0);
  }
}
