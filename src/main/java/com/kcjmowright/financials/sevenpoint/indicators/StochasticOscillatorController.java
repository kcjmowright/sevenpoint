package com.kcjmowright.financials.sevenpoint.indicators;

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
@RequestMapping("/stochastic")
@RequiredArgsConstructor
public class StochasticOscillatorController {

  private final QuoteService quoteService;

  @GetMapping("/{symbol}")
  public List<StochasticValue> getStochasticOscillatorValues(@PathVariable(name = "symbol") String symbol,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "start", required = false)
      LocalDateTime startInput,
      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @RequestParam(name = "end", required = false)
      LocalDateTime endInput,
      @RequestParam(name = "period", required = false)
      Integer periodInput) {

    LocalDateTime end = endInput == null ? LocalDateTime.now() : endInput;
    LocalDateTime start = startInput == null ? end.minusDays(60L) : startInput;
    List<Quote> quotes = quoteService.getQuotesBySymbolAndDateRange(symbol, start, end);
    Integer period = isNull(periodInput) ? CommodityChannelIndex.DEFAULT_PERIOD : periodInput;
    return new StochasticOscillator(quotes, period).getStochasticValues();
  }
}
