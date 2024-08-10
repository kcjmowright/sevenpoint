package com.kcjmowright.financials.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import com.kcjmowright.financials.domain.QuoteService;
import com.kcjmowright.financials.domain.Quote;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/quote")
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @RequestMapping(path = "/{ticker}", method = RequestMethod.GET)
    public List<Quote> getQuoteByTickerAndMarkBetween(@PathVariable(name = "ticker", required = true) String ticker, 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("start") LocalDateTime startInput, 
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam("end") LocalDateTime endInput) {

        LocalDateTime end = endInput == null ? LocalDateTime.now() : endInput;
        LocalDateTime start = startInput == null ? end.minusDays(1L) : startInput;
        return quoteService.getQuotesByTickerAndDateRange(ticker, start, end);
    }

}
