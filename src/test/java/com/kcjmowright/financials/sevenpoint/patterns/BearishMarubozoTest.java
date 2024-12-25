package com.kcjmowright.financials.sevenpoint.patterns;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.kcjmowright.financials.sevenpoint.company.QuoteParser.parse;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.Trend;

public class BearishMarubozoTest {

  @Test
  void shouldBeABearishMarubozo() {
    List<Quote> quotes = List.of(
        parse("1", "foo", "2024-02-06", "429.6100", "430.2200", "425.2200" ,"427.5900", "35846134", "427.5900"),
        parse("1", "foo", "2024-02-07", "430.4100", "432.8300", "429.0900" ,"431.9900", "37712661", "431.9900"),
        parse("1", "foo", "2024-02-08", "432.1100", "433.5600", "431.4200" ,"432.7900", "29889892", "432.7900"),
        parse("1", "foo", "2024-02-09", "433.9400", "437.8450", "433.1401" ,"437.0500", "36943905", "437.0500"),
        parse("1", "foo", "2024-02-12", "447.9400", "447.9400", "431.6500" ,"431.6500", "33203305", "431.6500"));

    BigDecimal slope = Trend.findSlope(quotes, quotes.size());
    assertTrue(BigDecimal.ZERO.compareTo(slope) < 0, "Expected zero to be less than slope");
    assertTrue(new BearishMarubozo().analyze(quotes, slope));
  }
}
