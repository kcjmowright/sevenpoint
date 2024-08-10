package com.kcjmowright.financials.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.kcjmowright.financials.domain.Quote;

public interface QuoteService {

    List<Quote> getQuotesByTickerAndDateRange(String ticker, LocalDateTime start, LocalDateTime end);

}
