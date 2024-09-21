package com.kcjmowright.financials.sevenpoint.indicators;

import static com.kcjmowright.financials.sevenpoint.indicators.AverageTrueRange.calculateTrueRange;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.Getter;

/**
 *
 * <ul>
 * <li><a href="https://www.investopedia.com/terms/a/adx.asp">Investopedia</a>
 * <li><a href="https://trendspider.com/learning-center/understanding-the-average-directional-index-adx/">Understanding the Average Directional Index</a>
 * <li><a href="https://www.youtube.com/watch?v=LKDJQLrXedg">YouTube</a>
 * </ul>
 */
@Getter
public class AverageDirectionalIndex {

  public static final int DEFAULT_PERIOD = 14;
  private final List<Quote> quotes;
  private final List<IndicatorValue> values = new ArrayList<>();
  private final int period;

  public AverageDirectionalIndex(List<Quote> quotes) {
    this(quotes, DEFAULT_PERIOD);
  }

  public AverageDirectionalIndex(List<Quote> quotes, int period) {
    this.quotes = Objects.requireNonNull(quotes, "Expected quotes");
    this.period = period;
    calculate();
  }

  void calculate() {
    int len = quotes.size();
    values.add(new IndicatorValue(quotes.get(0).getTimestamp(), null));
    for (int idx = 1; idx < len; idx++) {
      final Quote current = quotes.get(idx);
      final Quote previous = quotes.get(idx - 1);
      final BigDecimal highRange = current.getHigh().subtract(previous.getHigh());
      final BigDecimal lowRange = previous.getLow().subtract(current.getLow());
      final BigDecimal positiveDM = highRange.compareTo(lowRange) > 0 ? highRange : BigDecimal.ZERO;
      final BigDecimal negativeDM = highRange.compareTo(lowRange) < 0 ? lowRange : BigDecimal.ZERO;
      final BigDecimal trueRange = calculateTrueRange(current, previous);

    }

  }
}
