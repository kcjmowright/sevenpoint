package com.kcjmowright.financials.sevenpoint.patterns;

import static java.util.Objects.isNull;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.company.QuoteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pattern")
@RequiredArgsConstructor
public class CandleStickPatternsController {

  private final QuoteService quoteService;

  @GetMapping("/{symbol}")
  public CandlestickPatterns.Result getPatterns(@PathVariable(name = "symbol") String symbol, @DateTimeFormat(iso =
      DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "start", required = false) LocalDateTime startInput, @DateTimeFormat(iso =
      DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "end", required = false) LocalDateTime endInput,
      @RequestParam(name = "shortPeriod", required = false) Integer shortPeriodInput,
      @RequestParam(name = "longPeriod", required = false) Integer longPeriodInput) {
    LocalDateTime end = endInput == null ? LocalDateTime.now() : endInput;
    LocalDateTime start = startInput == null ? end.minusDays(60L) : startInput;
    List<Quote> quotes = quoteService.getQuotesBySymbolAndDateRange(symbol, start, end);
    Integer shortPeriod = isNull(shortPeriodInput) ? CandlestickPatterns.DEFAULT_SHORT_PERIOD : shortPeriodInput;
    Integer longPeriod = isNull(longPeriodInput) ? CandlestickPatterns.DEFAULT_LONG_PERIOD : longPeriodInput;
    return new CandlestickPatterns(quotes, shortPeriod, longPeriod).analyze();
  }
}
