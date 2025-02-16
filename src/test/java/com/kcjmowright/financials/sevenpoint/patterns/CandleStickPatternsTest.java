package com.kcjmowright.financials.sevenpoint.patterns;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import static com.kcjmowright.financials.sevenpoint.company.QuoteParser.parse;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CandleStickPatternsTest {

  private List<Quote> quotes;

  @BeforeEach
  void setup() throws Exception {
    Resource dataResource = new ClassPathResource("qqq-2024.csv");
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataResource.getInputStream()))) {
      List<String[]> values = csvReader.readAll();
      var data = values.subList(1, values.size());
      quotes = data.stream()
          .map(row -> parse("1", "QQQ", row[0], row[1], row[2], row[3], row[4], row[5], row[4]))
          .sorted(Comparator.comparing(Quote::getTimestamp)) // sort ascending
          .toList();
    }
  }

  @Test
  void test() {
    var len = quotes.size();
    List<String> results = new ArrayList<>();
    for (int idx = 0; idx + CandlestickPatterns.DEFAULT_LONG_PERIOD <= len; idx++) {
      CandlestickPatterns candlestickPatterns = new CandlestickPatterns(quotes.subList(idx, idx + CandlestickPatterns.DEFAULT_LONG_PERIOD));
      CandlestickPatterns.Result result = candlestickPatterns.analyze();
      assertNotNull(result);
      results.add("%s, %s, %s, %s, %s".formatted(result.timestamp(), result.longPeriodSlope(),
          result.shortPeriodSlope(), result.twoPeriodSlope(), result.patterns().stream().map(ICandlestickPattern::getName).toList()));
    }
    log.info("\n{}", String.join("\n", results));
  }

}
