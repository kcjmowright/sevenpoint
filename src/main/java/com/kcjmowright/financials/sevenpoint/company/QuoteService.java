package com.kcjmowright.financials.sevenpoint.company;

import java.time.LocalDateTime;
import java.util.List;

import com.kcjmowright.financials.sevenpoint.company.Quote;

public interface QuoteService {

    List<Quote> getQuotesBySymbolAndDateRange(String symbol, LocalDateTime start, LocalDateTime end);

    void loadQuote(String symbol);

    void deleteBySymbol(String symbol);

}
