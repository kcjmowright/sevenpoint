package com.kcjmowright.financials.sevenpoint.patterns;

import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.kcjmowright.financials.math.Line;
import com.kcjmowright.financials.math.LinearLeastSquares;
import com.kcjmowright.financials.math.Point;
import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.Getter;

/**
 * <a href="https://www.investopedia.com/articles/active-trading/092315/5-most-powerful-candlestick-patterns.asp">5 Most Powerful Candlestick Patterns</a>
 * <a href="https://www.ig.com/en/trading-strategies/16-candlestick-patterns-every-trader-should-know-180615">16 Candlestick Patterns Every Trader Should Know</a>
 * <a href="https://www.morpher.com/blog/candlestick-patterns">Candlestick Patterns</a>
 *
 */
@Getter
public class CandlestickPatterns {

  private static final List<ICandlestickPattern> patterns = List.of(
      new BearishEngulfing(),
      new BullishEngulfing(),
      new Hammer(),
      new HangingMan(),
      new InvertedHammer(),
      new ShootingStar(),
      new Doji(),
      new AbandonedBabyTop(),
      new AbandonedBabyBottom(),
      new BullishHarami(),
      new BearishHarami(),
      new StaleGreenLight(),
      new StaleRedLight());

  public static final int DEFAULT_SHORT_PERIOD = 5;
  public static final int DEFAULT_LONG_PERIOD = 20;

  private final List<Quote> quotes;
  private final int shortPeriod;
  private final int longPeriod;

  public record Result(LocalDateTime timestamp, Set<ICandlestickPattern> patterns) { }

  /**
   *
   * @param quotes a collection of {@code Quote}s.
   */
  public CandlestickPatterns(List<Quote> quotes) {
    this(quotes, DEFAULT_SHORT_PERIOD, DEFAULT_LONG_PERIOD);
  }

  /**
   *
   * @param quotes a list of {@code Quote}s assumed to be sorted by {@link Quote#getTimestamp} ascending.
   * @param shortPeriod the short period
   * @param longPeriod the long period
   * @throws IllegalArgumentException if the quotes size is less than the long period or the long period is shorter than the short period.
   */
  public CandlestickPatterns(List<Quote> quotes, int shortPeriod, int longPeriod) {
    if (shortPeriod < 5 || longPeriod < shortPeriod) {
      throw new IllegalArgumentException("Short period is less than the minimum of 5 or long period is shorter than short period.");
    }
    this.quotes = Objects.requireNonNull(quotes, "Expected a list of quotes");
    if (quotes.size() < longPeriod) {
      throw new IllegalArgumentException("Quotes size is less than the long period, %d".formatted(longPeriod));
    }
    this.shortPeriod = shortPeriod;
    this.longPeriod = longPeriod;
  }

  public Result analyze() {
    final int size = quotes.size();
    final List<Quote> quoteSublist = size == longPeriod ? quotes : quotes.subList(size - longPeriod, size);
    final List<Point> points = quoteSublist.stream().flatMap(q -> {
      var x = BigDecimal.valueOf(q.getTimestamp().toEpochSecond(ZoneOffset.UTC));
      return Stream.of(new Point(x, q.getOpen()), new Point(x, q.getClose()));
    }).toList();
    final Line shortLine = LinearLeastSquares.linearLeastSquares(points.subList(longPeriod - shortPeriod, longPeriod)).line();
    final BigDecimal shortSlope = shortLine.slope();
    return new Result(quoteSublist.getLast().getTimestamp(), patterns.stream()
        .collect(toMap(Function.identity(), p -> p.analyze(quoteSublist, shortSlope))).entrySet().stream()
        .filter(Map.Entry::getValue)
        .map(Map.Entry::getKey).collect(Collectors.toSet()));
  }
}
