package com.kcjmowright.financials.sevenpoint.analysis;

import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * A bounce or breakout of the role reversal.  Divergence above or below the role reversal.
 */
@NoArgsConstructor
@Slf4j
public class RoleReversalEvidence implements Evidence {
  @Override
  public int process(List<Quote> quotes) {
    log.info("Processing role reversal evidence");
    return 0;
  }
}
