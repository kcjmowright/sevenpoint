package com.kcjmowright.financials.sevenpoint.patterns;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.kcjmowright.financials.sevenpoint.company.QuoteParser.parse;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.Trend;

public class AbandonedBabyTopTest {
  @Test
  void shouldBeAnAbandonedBabyTopTest() {
    List<Quote> quotes = List.of(
        parse("1", "foo", "2024-08-15", "468.7600", "474.8200", "468.3800", "474.4200", "38280565", "474.4200"),
        parse("2", "foo", "2024-08-16", "472.6200", "476.4100", "471.6500", "475.0300", "38383623", "475.0300"),
        parse("3", "foo", "2024-08-19", "475.1700", "481.3100", "473.3700", "481.2700", "23737674", "481.2700"),
        parse("4", "foo", "2024-08-20", "480.3500", "482.9400", "478.5500", "480.2600", "29209340", "480.2600"),
        parse("5", "foo", "2024-08-21", "481.0500", "484.3700", "479.3200", "482.5000", "25658787", "482.5000")
    );
    BigDecimal slope = Trend.findPriceSlope(quotes, quotes.size());
    assertTrue(slope.compareTo(BigDecimal.ZERO) > 0);
    assertTrue(new AbandonedBabyTop().analyze(quotes, slope));
  }
}
