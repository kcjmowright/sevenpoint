package com.kcjmowright.financials.sevenpoint.company;

import java.time.LocalDateTime;
import java.util.List;

public interface QuoteService {

  List<Quote> getQuotesBySymbolAndDateRange(String symbol, LocalDateTime start, LocalDateTime end);

  void loadQuote(String symbol);

  void deleteBySymbol(String symbol);
}
