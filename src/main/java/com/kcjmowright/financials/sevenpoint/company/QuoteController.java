package com.kcjmowright.financials.sevenpoint.company;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/quote")
@RequiredArgsConstructor
@Slf4j
public class QuoteController {

  private final QuoteService quoteService;

  @GetMapping("/{symbol}")
  public List<Quote> getQuoteBySymbolAndMarkBetween(@PathVariable(name = "symbol") String symbol, @DateTimeFormat(iso =
      DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "start", required = false) LocalDateTime startInput, @DateTimeFormat(iso =
      DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "end", required = false) LocalDateTime endInput) {

    LocalDateTime end = endInput == null ? LocalDateTime.now() : endInput;
    LocalDateTime start = startInput == null ? end.minusDays(1L) : startInput;
    return quoteService.getQuotesBySymbolAndDateRange(symbol, start, end);
  }

  @PutMapping("/{symbol}")
  public void putQuoteBySymbol(@PathVariable(name = "symbol") String symbol) {
    quoteService.loadQuote(symbol);
  }

  @DeleteMapping("/{symbol}")
  @Transactional
  public void deleteQuoteBySymbol(@PathVariable(name = "symbol") String symbol) {
    quoteService.deleteBySymbol(symbol);
  }
}
