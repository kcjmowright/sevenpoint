package com.kcjmowright.financials.sevenpoint.indicators;

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
@RequestMapping("/price-gap")
@RequiredArgsConstructor
public class PriceGapController {

  private final QuoteService quoteService;

  @GetMapping("/{symbol}")
  public List<PriceGap> getOpenPriceGaps(@PathVariable(name = "symbol") String symbol,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "start", required = false) LocalDateTime startInput,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "end", required = false)
      LocalDateTime endInput) {

    LocalDateTime end = endInput == null ? LocalDateTime.now() : endInput;
    LocalDateTime start = startInput == null ? end.minusDays(60L) : startInput;
    List<Quote> quotes = quoteService.getQuotesBySymbolAndDateRange(symbol, start, end);
    return new PriceGaps(quotes).getOpenedPriceGaps();
  }
}
