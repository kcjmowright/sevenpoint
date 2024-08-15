package com.kcjmowright.financials.sevenpoint.quotes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.kcjmowright.financials.sevenpoint.quotes.Quote;
import com.kcjmowright.financials.sevenpoint.quotes.QuoteRepository;
import com.kcjmowright.financials.sevenpoint.quotes.QuoteService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;

    @Override
    public List<Quote> getQuotesByTickerAndDateRange(String ticker, LocalDateTime start, LocalDateTime end) {
        return quoteRepository.findByTickerAndMarkBetween(ticker, start, end);
    }
}
