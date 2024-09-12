package com.kcjmowright.financials.sevenpoint.company;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kcjmowright.financials.alphavantage.AlphavantageRestClient;
import com.kcjmowright.financials.alphavantage.DataType;
import com.kcjmowright.financials.alphavantage.OutputSize;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuoteServiceImpl implements QuoteService {

  private final QuoteRepository quoteRepository;

  private final AlphavantageRestClient client = new AlphavantageRestClient();
  @Value("${ALPHAVANTAGE_KEY:null}")
  private String apiKey;

  @Override
  public List<Quote> getQuotesBySymbolAndDateRange(String symbol, LocalDateTime start, LocalDateTime end) {
    return quoteRepository.findBySymbolAndTimestampBetween(symbol, start, end);
  }

  @Override
  public void loadQuote(String symbol) {
    log.info("Fetching quotes for {}", symbol);
    Map<String, Map<String, Map<String, String>>> result = client.getTimeSeriesDaily(symbol, this.apiKey, OutputSize.COMPACT, DataType.JSON);
    log.info("Saving quotes for {}", symbol);
    result.get("Time Series (Daily)").forEach((key, value) -> {
      Quote quote = new Quote();
      quote.setSymbol(symbol);
      quote.setTimestamp(LocalDate.parse(key, DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay());
      quote.setOpen(new BigDecimal(value.get("1. open")));
      quote.setHigh(new BigDecimal(value.get("2. high")));
      quote.setLow(new BigDecimal(value.get("3. low")));
      quote.setClose(new BigDecimal(value.get("4. close")));
      quote.setVolume(Long.valueOf(value.get("5. volume")));
      quoteRepository.save(quote);
    });
  }

  @Override
  public void deleteBySymbol(String symbol) {
    quoteRepository.deleteBySymbol(symbol);
  }
}
