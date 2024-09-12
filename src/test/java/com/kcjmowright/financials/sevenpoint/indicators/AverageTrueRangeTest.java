package com.kcjmowright.financials.sevenpoint.indicators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.kcjmowright.financials.util.Strings.emptyOrNull;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.kcjmowright.financials.config.DateTimeConfig;
import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AverageTrueRangeTest {

  static List<Quote> quotes = new ArrayList<>();
  static List<AverageIndicatorValue> expectedValues = new ArrayList<>();

  @BeforeAll
  static void beforeAll() throws Exception {
    Resource dataResource = new ClassPathResource("indicators/average-true-range.csv");
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataResource.getInputStream()))) {
      List<String[]> all = csvReader.readAll();
      List<String[]> data = all.subList(1, all.size());
      data.forEach(row -> {
        var quote = new Quote();
        var timestamp = LocalDate.parse(row[0], DateTimeConfig.dateFormatter).atStartOfDay();
        quote.setSymbol("foo");
        quote.setTimestamp(timestamp);
        quote.setOpen(new BigDecimal(row[1]));
        quote.setHigh(new BigDecimal(row[2]));
        quote.setLow(new BigDecimal(row[3]));
        quote.setClose(new BigDecimal(row[4]));
        quote.setVolume(Long.parseLong(row[5]));
        quotes.add(quote);

        var aiv = new AverageIndicatorValue();
        aiv.setTimestamp(timestamp);
        if (!emptyOrNull(row[6])) {
          aiv.setValue(new BigDecimal(row[6]));
        }
        if (!emptyOrNull(row[7])) {
          aiv.setAverage(new BigDecimal(row[7]));
        }
        expectedValues.add(aiv);
      });
    }
    quotes.sort(Comparator.comparing(Quote::getTimestamp));
    expectedValues.sort(Comparator.comparing(IndicatorValue::getTimestamp));
  }

  @Test
  void test() {
    AverageTrueRange averageTrueRange = new AverageTrueRange(quotes);
    var actualValues = averageTrueRange.getValues();
    for (int i = 0; i < actualValues.size(); i++) {
      AverageIndicatorValue actualValue = actualValues.get(i);
      if (log.isDebugEnabled()) {
        log.debug("{}\t,{},{}", actualValue.getTimestamp(), actualValue.getValue(), actualValue.getAverage());
      }
      assertEquals(expectedValues.get(i), actualValue);
    }
  }
}
