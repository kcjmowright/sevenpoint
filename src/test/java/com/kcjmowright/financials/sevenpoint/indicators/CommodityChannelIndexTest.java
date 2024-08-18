package com.kcjmowright.financials.sevenpoint.indicators;

import static java.util.function.Predicate.not;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import com.kcjmowright.financials.util.Strings;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommodityChannelIndexTest {
  static List<Quote> quotes = new ArrayList<>();
  static List<String[]> data;

  @BeforeAll
  static void beforeAll() throws Exception {
    Resource dataResource = new ClassPathResource("indicators/cci.csv");
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataResource.getInputStream()))) {
      List<String[]> all = csvReader.readAll();
      data = all.subList(1, all.size());
      data.forEach(row -> {
        var quote = new Quote();
        quote.setSymbol("foo");
        quote.setMark(LocalDate.parse(row[0], DateTimeConfig.dateFormatter).atStartOfDay());
        quote.setHigh(new BigDecimal(row[1]));
        quote.setLow(new BigDecimal(row[2]));
        quote.setClose(new BigDecimal(row[3]));
        quotes.add(quote);
      });
    }
  }

  @Test
  void test() {
    CommodityChannelIndex cci = new CommodityChannelIndex(quotes);
    List<BigDecimal> expected = data.stream().map(row -> row[7]).filter(not(Strings::emptyOrNull)).map(BigDecimal::new).toList();
    for (int i = 0; i < expected.size(); i++) {
      assertEquals(expected.get(i), cci.getValues().get(i).getCci());
    }
  }
}
