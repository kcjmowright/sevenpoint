package com.kcjmowright.financials.sevenpoint.patterns;

import static java.util.stream.Collectors.toMap;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.sevenpoint.indicators.Trend;

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
      new AbandonedBabyBottom(),
      new AbandonedBabyTop(),
      new BearishEngulfing(),
      new BearishHarami(),
      new BearishMarubozo(),
      new BullishEngulfing(),
      new BullishHarami(),
      new BullishMarubozo(),
      new Doji(),
      new Hammer(),
      new HangingMan(),
      new InvertedHammer(),
      new ShootingStar(),
      new StaleGreenLight(),
      new StaleRedLight());

  public static final int DEFAULT_SHORT_PERIOD = 5;
  public static final int DEFAULT_LONG_PERIOD = 20;

  private final List<Quote> quotes;
  private final int shortPeriod;
  private final int longPeriod;

  // @formatter:off
  public record Result(
      LocalDateTime timestamp,
      BigDecimal longPeriodSlope,
      BigDecimal shortPeriodSlope,
      BigDecimal twoPeriodSlope,
      Set<ICandlestickPattern> patterns) { }
  // @formatter:on

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
      throw new IllegalArgumentException("Short period is less than the minimum of %d or long period is shorter than short period.".formatted(DEFAULT_SHORT_PERIOD));
    }
    this.quotes = Objects.requireNonNull(quotes, "Expected a list of quotes");
    if (quotes.size() < longPeriod) {
      throw new IllegalArgumentException("Quotes size is less than the long period, %d".formatted(longPeriod));
    }
    this.shortPeriod = shortPeriod;
    this.longPeriod = longPeriod;
  }

  public Result analyze() {
    final List<Quote> longPeriodSublist = quotes.size() <= longPeriod ? quotes
        : quotes.subList(quotes.size() - longPeriod, quotes.size());
    final BigDecimal longSlope = Trend.findPriceSlope(longPeriodSublist, longPeriod);
    final List<Quote> shortPeriodSublist = longPeriodSublist.subList(longPeriodSublist.size() - shortPeriod, longPeriodSublist.size());
    final BigDecimal shortSlope = Trend.findPriceSlope(shortPeriodSublist, shortPeriod);
    final BigDecimal twoPeriodSlope = Trend.findPriceSlope(shortPeriodSublist, 2);
    final Set<ICandlestickPattern> candleSticks = patterns.stream()
        .collect(toMap(Function.identity(), p -> p.analyze(shortPeriodSublist, shortSlope))).entrySet().stream()
        .filter(Map.Entry::getValue)
        .map(Map.Entry::getKey).collect(Collectors.toSet());
    return new Result(longPeriodSublist.getLast().getTimestamp(), longSlope, shortSlope, twoPeriodSlope, candleSticks);
  }
}
