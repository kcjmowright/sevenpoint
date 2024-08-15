package com.kcjmowright.financials.sevenpoint.quotes;

import java.time.LocalDateTime;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.quotes.Quote;

public interface QuoteService {

    List<Quote> getQuotesByTickerAndDateRange(String ticker, LocalDateTime start, LocalDateTime end);

}
