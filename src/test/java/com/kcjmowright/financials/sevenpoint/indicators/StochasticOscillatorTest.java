package com.kcjmowright.financials.sevenpoint.indicators;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static com.kcjmowright.financials.util.Strings.emptyOrNull;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class StochasticOscillatorTest {

  static List<Quote> quotes = new ArrayList<>();
  // idx:0, date:1, high:2, low:3, max:4, min:5, close:6, k:7, d:8
  static List<String[]> expected;

  @BeforeAll
  static void beforeAll() throws Exception {
    Resource dataResource = new ClassPathResource("indicators/stochastic-oscillator-test.csv");
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataResource.getInputStream()))) {
      expected = csvReader.readAll();
      expected.forEach(row -> {
        // log.info("{}", row);
        var quote = new Quote();
        quote.setMark(LocalDate.parse(row[1], DateTimeConfig.dateFormatter).atStartOfDay());
        quote.setHigh(new BigDecimal(row[2]));
        quote.setLow(new BigDecimal(row[3]));
        if (!emptyOrNull(row[6])) {
          quote.setClose(new BigDecimal(row[6]));
        }
        quotes.add(quote);
      });
    }
  }

  @Test
  void testShouldSetDefaultValues() {
    var stochasticOscillator = new StochasticOscillator();
    assertEquals(List.of(), stochasticOscillator.getValues());
    assertEquals(14, stochasticOscillator.getPeriod());
    assertEquals(List.of(), stochasticOscillator.getQuotes());
  }

  @Test
  void testShouldCalculate14DayStochasticOscillator() {
    var stochasticOscillator = new StochasticOscillator(quotes);
    assertEquals(expected.size() - stochasticOscillator.getPeriod() + 1, stochasticOscillator.getValues().size());
    for (int idx = 0; idx < stochasticOscillator.getValues().size(); idx++) {
      var expectedValue = expected.get(idx + stochasticOscillator.getPeriod() - 1);
      var value = stochasticOscillator.getValues().get(idx);
      if (!emptyOrNull(expectedValue[8]) /* d */) {
        assertEquals(new BigDecimal(expectedValue[8]), value.getD());
      }
      if (!emptyOrNull(expectedValue[7]) /* k */) {
        assertEquals(new BigDecimal(expectedValue[7]), value.getK());
      }
      assertEquals(LocalDate.parse(expectedValue[1], DateTimeConfig.dateFormatter).atStartOfDay(), value.getDate());
    }
  }
}
