package com.kcjmowright.financials.sevenpoint.indicators;

import static com.kcjmowright.financials.math.BigDecimalAverage.average;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.Getter;

/**
 * <a href="https://www.investopedia.com/terms/a/atr.asp">Investopedia - Average True Range</a>
 * <p>
 * (1 / n) * sum(TR)
 * <p>
 * TR = max((H−L),(∣H−C|),(|L-C|))
 */
@Getter
public class AverageTrueRange {

  public static final int DEFAULT_PERIOD = 14;

  private final List<Quote> quotes;

  private final List<AverageIndicatorValue> values = new ArrayList<>();

  private final int period;

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   */
  public AverageTrueRange(List<Quote> quotes) {
    this(quotes, DEFAULT_PERIOD);
  }

  /**
   * Constructor
   *
   * @param quotes a list of {@link Quote} objects assumed to be in chronological order ascending.
   * @param period the length of the period to consider for the average.  Default is DEFAULT_PERIOD.
   */
  public AverageTrueRange(List<Quote> quotes, int period) {
    this.quotes = Objects.requireNonNull(quotes, "Expected quotes");
    this.period = period;
    calculate();
  }

  public static BigDecimal calculateTrueRange(Quote current, Quote previous) {
    final BigDecimal hl = current.getHigh().subtract(current.getLow());
    final BigDecimal hcp = current.getHigh().subtract(previous.getClose()).abs();
    final BigDecimal lcp = current.getLow().subtract(previous.getClose()).abs();
    return hl.max(hcp).max(lcp);
  }

  void calculate() {
    if (quotes.isEmpty()) {
      return;
    }
    int len = quotes.size();
    values.add(new AverageIndicatorValue(quotes.getFirst().getTimestamp(), null, null));
    for (int i = 1; i < len; i++) {
      final Quote current = quotes.get(i);
      final Quote previous = quotes.get(i - 1);
      final AverageIndicatorValue aiv = new AverageIndicatorValue(current.getTimestamp(), calculateTrueRange(current, previous), null);
      values.add(aiv);
      if (values.size() > period) {
        aiv.setAverage(average(values.subList(i - period + 1, i + 1).stream().map(AverageIndicatorValue::getValue).toList()));
      }
    }
  }
}
