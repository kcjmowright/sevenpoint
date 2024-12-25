package com.kcjmowright.financials.sevenpoint.indicators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.kcjmowright.financials.util.Strings.emptyOrNull;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.kcjmowright.financials.config.DateTimeConfig;
import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.util.Dates;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MovingAverageTest {

  static List<Quote> quotes = new ArrayList<>();
  static List<IndicatorValue> expectedValues = new ArrayList<>();

  @BeforeAll
  static void beforeAll() throws Exception {
    Resource dataResource = new ClassPathResource("indicators/moving-average.csv");
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataResource.getInputStream()))) {
      List<String[]> all = csvReader.readAll();
      List<String[]> data = all.subList(1, all.size());
      data.forEach(row -> {
        var quote = new Quote();
        quote.setSymbol("foo");
        LocalDateTime timestamp = Dates.toDate(row[0]);
        quote.setTimestamp(timestamp);
        quote.setOpen(new BigDecimal(row[1]));
        quote.setHigh(new BigDecimal(row[2]));
        quote.setLow(new BigDecimal(row[3]));
        quote.setClose(new BigDecimal(row[4]));
        quote.setVolume(Long.parseLong(row[5]));
        quotes.add(quote);

        var iv = new IndicatorValue();
        iv.setTimestamp(timestamp);
        if (!emptyOrNull(row[6])) {
          iv.setValue(new BigDecimal(row[6]));
        }
        expectedValues.add(iv);
      });
    }
    quotes.sort(Comparator.comparing(Quote::getTimestamp));
    expectedValues.sort(Comparator.comparing(IndicatorValue::getTimestamp));
  }

  @Test
  void test() {
    MovingAverage ma = new MovingAverage(quotes);
    var actualValues = ma.getValues();
    for (int i = 0; i < actualValues.size(); i++) {
      IndicatorValue actualValue = actualValues.get(i);
      if (log.isDebugEnabled()) {
        log.debug("{}\t,{}", actualValue.getTimestamp(), actualValue.getValue());
      }
      assertEquals(expectedValues.get(i), actualValue);
    }
  }
}
