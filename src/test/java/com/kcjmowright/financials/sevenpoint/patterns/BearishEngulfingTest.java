package com.kcjmowright.financials.sevenpoint.patterns;

import static org.junit.jupiter.api.Assertions.assertTrue;

import static com.kcjmowright.financials.sevenpoint.company.QuoteParser.parse;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.Trend;

public class BearishEngulfingTest {

  @Test
  void shouldBeABearishEngulfing() {
    List<Quote> quotes = List.of(
        parse("1", "foo", "2024-02-12", "436.9400", "439.1400", "434.6500", "435.3400", "33203305", "435.3400"),
        parse("2", "foo", "2024-02-13", "427.2800", "431.2700", "425.3305", "428.5500", "64491669", "428.5500"),
        parse("3", "foo", "2024-02-14", "431.2600", "433.6500", "428.8799", "433.2200", "45092722", "433.2200"),
        parse("4", "foo", "2024-02-15", "433.9200", "434.9800", "431.3300", "434.5100", "38796137", "434.5100"),
        parse("5", "foo", "2024-02-16", "434.8900", "434.9900", "429.8500", "430.5700", "53716475", "430.5700")
    );
    BigDecimal slope = Trend.findPriceSlope(quotes, quotes.size());
    assertTrue(slope.compareTo(BigDecimal.ZERO) < 0);
    assertTrue(new BearishEngulfing().analyze(quotes, slope));
  }
}
