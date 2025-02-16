package com.kcjmowright.financials.sevenpoint.analysis;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.util.Dates;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SevenPointTest {

  static List<Quote> quotes = new ArrayList<>();

  @Autowired
  private SevenPoint sevenPoint;

  @BeforeAll
  static void beforeAll() throws Exception {
    Resource dataResource = new ClassPathResource("qqq-2024.csv");
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataResource.getInputStream()))) {
      List<String[]> all = csvReader.readAll();
      List<String[]> data = all.subList(1, all.size());
      data.forEach(row -> {
        LocalDateTime timestamp = Dates.toDate(row[0]);
        var quote = new Quote();
        quote.setSymbol("foo");
        quote.setTimestamp(timestamp);
        quote.setOpen(new BigDecimal(row[1]));
        quote.setHigh(new BigDecimal(row[2]));
        quote.setLow(new BigDecimal(row[3]));
        quote.setClose(new BigDecimal(row[4]));
        quote.setVolume(Long.parseLong(row[5]));
        quotes.add(quote);
      });
    }
    quotes.sort(Comparator.comparing(Quote::getTimestamp));
  }

  @Test
  void test() {
    int result = sevenPoint.analyze(quotes);
    assertEquals(result, -2);
  }
}
