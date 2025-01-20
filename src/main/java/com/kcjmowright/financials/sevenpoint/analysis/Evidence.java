package com.kcjmowright.financials.sevenpoint.analysis;

import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public interface Evidence {

  /**
   *  @param quotes List of quotes.
   *  @return -1 bear, 0 neutral, 1 bull.
   */
  int process(List<Quote> quotes);
}
