package com.kcjmowright.financials.sevenpoint.company;

import java.math.BigDecimal;

import com.kcjmowright.financials.sevenpoint.util.Dates;

public class QuoteParser {

  public static Quote parse(String id, String symbol, String timestamp, String open, String high, String low, String close, String volume, String adjClose) {
    return new Quote(Long.parseLong(id), symbol, Dates.toDate(timestamp), new BigDecimal(high), new BigDecimal(low), new BigDecimal(open),
        new BigDecimal(close), Long.parseLong(volume), new BigDecimal(adjClose));
  }
}
