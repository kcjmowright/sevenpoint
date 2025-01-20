package com.kcjmowright.financials.sevenpoint.analysis;

import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public interface SevenPoint {

  int analyze(List<Quote> quotes);
}
