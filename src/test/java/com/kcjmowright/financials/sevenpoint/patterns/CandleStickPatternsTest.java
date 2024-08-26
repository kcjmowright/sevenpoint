package com.kcjmowright.financials.sevenpoint.patterns;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.kcjmowright.financials.config.DateTimeConfig;
import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.opencsv.CSVReader;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CandleStickPatternsTest {

  private List<Quote> quotes = new ArrayList<>();

  @BeforeEach
  void setup() throws Exception {
    Resource dataResource = new ClassPathResource("daily_QQQ.csv");
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataResource.getInputStream()))) {
      List<String[]> values = csvReader.readAll();
      var data = values.subList(1, values.size()).reversed();
      data.forEach(row -> {
        var quote = new Quote();
        quote.setSymbol("QQQ");
        quote.setMark(toDate(row[0]));
        quote.setOpen(new BigDecimal(row[1]));
        quote.setHigh(new BigDecimal(row[2]));
        quote.setLow(new BigDecimal(row[3]));
        quote.setClose(new BigDecimal(row[4]));
        quote.setVolume(Long.parseLong(row[5]));
        quotes.add(quote);
      });
    }
  }

  @Test
  void test() {
    var len = quotes.size();
    for (int idx = 0; idx + CandlestickPatterns.DEFAULT_LONG_PERIOD < len; idx++) {
      CandlestickPatterns candlestickPatterns = new CandlestickPatterns(quotes.subList(idx, idx + CandlestickPatterns.DEFAULT_LONG_PERIOD));
      CandlestickPatterns.Result result = candlestickPatterns.analyze();
      assertNotNull(result);
      log.info("timestamp: {}, patterns: {}", result.timestamp(), result.patterns().stream().map(p -> p.getClass().getSimpleName() + ".class").toList());
    }
  }

  private LocalDateTime toDate(String input) {
    return LocalDate.parse(input, DateTimeConfig.dateFormatter).atStartOfDay();
  }

//  List<CandlestickPatterns.Result> expected = List.of(
//      new CandlestickPatterns.Result(toDate("1999-11-30"), Set.of(BearishEngulfing.class.newInstance())),
//      toDate("1999-12-01"), Set.of(BearishEngulfing.class),
//      Set.of(Doji.class),
//      Set.of(Doji.class),
//      Set.of(Doji.class),
//    Set.of(ShootingStar.class),
//    Set.of(Doji.class, BearishEngulfing.class),
//    Set.of(Doji.class, AbandonedBabyTop.class),
//    Set.of(BearishEngulfing.class),
//    Set.of(ShootingStar.class, BearishEngulfing.class)
//  );
  /*
      1999-11-29T00:00, []
      1999-11-30T00:00, [BearishEngulfing.class]
      1999-12-01T00:00, [BearishEngulfing.class]
      1999-12-02T00:00, []
      1999-12-03T00:00, [Doji.class]
      1999-12-06T00:00, [Doji.class]
      1999-12-07T00:00, [Doji.class]
      1999-12-08T00:00, []
      1999-12-09T00:00, [BearishEngulfing.class, HangingMan.class]
      1999-12-10T00:00, [Doji.class, BearishEngulfing.class, StaleRedLight.class]
      1999-12-13T00:00, [BullishEngulfing.class]
      1999-12-14T00:00, []
      1999-12-15T00:00, [BullishEngulfing.class, ShootingStar.class]
      1999-12-16T00:00, [ShootingStar.class]
      1999-12-17T00:00, []
      1999-12-20T00:00, [ShootingStar.class]
      1999-12-21T00:00, [ShootingStar.class]
      1999-12-22T00:00, [BullishEngulfing.class, Doji.class]
      1999-12-23T00:00, [Doji.class, AbandonedBabyBottom.class]
      1999-12-27T00:00, [BearishEngulfing.class, HangingMan.class]
      1999-12-28T00:00, [BearishEngulfing.class]
      1999-12-29T00:00, [ShootingStar.class]
      1999-12-30T00:00, []
      1999-12-31T00:00, [BearishEngulfing.class]
      2000-01-03T00:00, [HangingMan.class]
      2000-01-04T00:00, [StaleRedLight.class]
      2000-01-05T00:00, [Doji.class]
      2000-01-06T00:00, [AbandonedBabyTop.class]
      2000-01-07T00:00, [ShootingStar.class]
      2000-01-10T00:00, []
      2000-01-11T00:00, []
      2000-01-12T00:00, [BearishEngulfing.class]
      2000-01-13T00:00, [ShootingStar.class]
      2000-01-14T00:00, [Hammer.class]
      2000-01-18T00:00, []
      2000-01-19T00:00, [BullishEngulfing.class, ShootingStar.class, StaleGreenLight.class]
      2000-01-20T00:00, [BullishEngulfing.class, Doji.class]
      2000-01-21T00:00, [Hammer.class, AbandonedBabyBottom.class]
      2000-01-24T00:00, [ShootingStar.class]
      2000-01-25T00:00, [BearishEngulfing.class]
      2000-01-26T00:00, [ShootingStar.class, BearishEngulfing.class]
      2000-01-27T00:00, [Hammer.class]
      2000-01-28T00:00, [ShootingStar.class]
      2000-01-31T00:00, [ShootingStar.class]
      2000-02-01T00:00, [ShootingStar.class]
      2000-02-02T00:00, []
      2000-02-03T00:00, [ShootingStar.class, StaleGreenLight.class]
      2000-02-04T00:00, [Doji.class, StaleGreenLight.class]
      2000-02-07T00:00, [ShootingStar.class, AbandonedBabyBottom.class, StaleGreenLight.class]
      2000-02-08T00:00, [StaleGreenLight.class]
      2000-02-09T00:00, []
      2000-02-10T00:00, [ShootingStar.class]
      2000-02-11T00:00, [BullishEngulfing.class, ShootingStar.class]
      2000-02-14T00:00, [Hammer.class]
      2000-02-15T00:00, [HangingMan.class]
      2000-02-16T00:00, [Doji.class]
      2000-02-17T00:00, [Doji.class]
      2000-02-18T00:00, [BearishEngulfing.class]
      2000-02-22T00:00, [Doji.class, BearishEngulfing.class]
      2000-02-23T00:00, []
      2000-02-24T00:00, [ShootingStar.class]
      2000-02-25T00:00, []
      2000-02-28T00:00, [Doji.class]
      2000-02-29T00:00, [ShootingStar.class, AbandonedBabyBottom.class]
      2000-03-01T00:00, [Doji.class]
      2000-03-02T00:00, [ShootingStar.class, BearishEngulfing.class]
      2000-03-03T00:00, [ShootingStar.class]
      2000-03-06T00:00, [Doji.class]
      2000-03-07T00:00, [ShootingStar.class, AbandonedBabyBottom.class]
      2000-03-08T00:00, [Doji.class, BearishEngulfing.class]
      2000-03-09T00:00, [ShootingStar.class, AbandonedBabyTop.class]
      2000-03-10T00:00, [BearishHarami.class, Doji.class]
      2000-03-13T00:00, []
      2000-03-14T00:00, [ShootingStar.class, BearishEngulfing.class]
      2000-03-15T00:00, [ShootingStar.class]
      2000-03-16T00:00, [ShootingStar.class]
      2000-03-17T00:00, []
      2000-03-20T00:00, [ShootingStar.class]
      2000-03-21T00:00, [ShootingStar.class]
      2000-03-22T00:00, [Doji.class]
      2000-03-23T00:00, []
      2000-03-24T00:00, [Doji.class, StaleGreenLight.class]
      2000-03-27T00:00, [Doji.class, AbandonedBabyBottom.class]
      2000-03-28T00:00, [ShootingStar.class]
      2000-03-29T00:00, [ShootingStar.class]
      2000-03-30T00:00, [Doji.class, StaleRedLight.class]
      2000-03-31T00:00, [HangingMan.class]
      2000-04-03T00:00, []
      2000-04-04T00:00, [Hammer.class]
      2000-04-05T00:00, []
      2000-04-06T00:00, [Doji.class]
      2000-04-07T00:00, []
      2000-04-10T00:00, [ShootingStar.class, BearishEngulfing.class]
      2000-04-11T00:00, [Doji.class]
      2000-04-12T00:00, []
      2000-04-13T00:00, []
      2000-04-14T00:00, []
      2000-04-17T00:00, [BullishEngulfing.class]
      2000-04-18T00:00, [BullishEngulfing.class, ShootingStar.class]
      2000-04-19T00:00, []
      2000-04-20T00:00, [ShootingStar.class, BearishEngulfing.class]
      2000-04-24T00:00, [HangingMan.class]
      2000-04-25T00:00, [ShootingStar.class]
  */
}
