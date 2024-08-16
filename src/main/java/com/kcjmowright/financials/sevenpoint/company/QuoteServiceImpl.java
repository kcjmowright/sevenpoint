package com.kcjmowright.financials.sevenpoint.company;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kcjmowright.financials.alphavantage.AlphavantageRestClient;
import com.kcjmowright.financials.alphavantage.DataType;
import com.kcjmowright.financials.alphavantage.OutputSize;
import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.company.QuoteRepository;
import com.kcjmowright.financials.sevenpoint.company.QuoteService;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
@Slf4j
public class QuoteServiceImpl implements QuoteService {

    private final QuoteRepository quoteRepository;

    private AlphavantageRestClient client = new AlphavantageRestClient();
    @Value("${ALPHAVANTAGE_KEY:null}")
    private String apiKey;

    @Override
    public List<Quote> getQuotesBySymbolAndDateRange(String symbol, LocalDateTime start, LocalDateTime end) {
        return quoteRepository.findBySymbolAndMarkBetween(symbol, start, end);
    }

    @Override
    public void loadQuote(String symbol) {
        Map<String, Map<String, Map>> result = 
            client.getTimeSeriesDaily(symbol, this.apiKey, OutputSize.COMPACT, DataType.JSON);
        result.get("Time Series (Daily)").entrySet().forEach(e -> {
            Map<String, String> value = e.getValue();
            Quote quote = new Quote();
            quote.setMark(LocalDateTime.parse(e.getKey(), DateTimeFormatter.ISO_LOCAL_DATE));
            quote.setOpen(new BigDecimal(value.get("1. open")));
            quote.setHigh(new BigDecimal(value.get("2. high")));
            quote.setLow(new BigDecimal(value.get("3. low")));
            quote.setClose(new BigDecimal(value.get("4. close")));
            quote.setVolume(Long.valueOf(value.get("5. volume")));
            log.info(quote.toString());
        });
        
    }
}
