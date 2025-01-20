package com.kcjmowright.financials.sevenpoint.patterns;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.kcjmowright.financials.sevenpoint.company.QuoteParser.parse;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.Trend;

public class AbandonedBabyBottomTest {

  @Test
  void shouldBeAnAbandonedBabyBottomTest() {
    List<Quote> quotes = List.of(
        parse("1", "foo", "2003-07-28", "31.8600", "31.9900", "31.6000", "31.8900", "56180800", "31.8900"),
        parse("2", "foo", "2003-07-29", "31.9300", "32.0200", "31.2400", "31.6500", "80560100", "31.6500"),
        parse("3", "foo", "2003-07-30", "31.7100", "31.7400", "31.3300", "31.4200", "56617300", "31.4200"),
        parse("4", "foo", "2003-07-31", "31.7900", "32.3300", "31.5200", "31.8000", "85544100", "31.8000"),
        parse("5", "foo", "2003-08-01", "31.6900", "31.7900", "31.3000", "31.4600", "73545400", "31.4600")
    );
    BigDecimal slope = Trend.findPriceSlope(quotes, quotes.size());
    assertTrue(slope.compareTo(BigDecimal.ZERO) < 0);
    assertTrue(new AbandonedBabyBottom().analyze(quotes, slope));
  }
}
