package com.kcjmowright.financials.sevenpoint.indicators;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import com.kcjmowright.financials.math.LinearLeastSquares;
import com.kcjmowright.financials.math.Point;
import com.kcjmowright.financials.sevenpoint.company.Quote;

public class Trend {

  private static final Function<Quote, Stream<Point>> getPoints = q -> {
    var x = BigDecimal.valueOf(q.getTimestamp().toEpochSecond(ZoneOffset.UTC));
    return Stream.of(new Point(x, q.getOpen()), new Point(x, q.getClose()));
  };

  public static BigDecimal findSlope(List<Quote> q, int periodSize) {
    final List<Quote> sublist = q.size() <= periodSize ? q
        : q.subList(q.size() - periodSize, q.size());
    final List<Point> points = sublist.stream().flatMap(getPoints).toList();
    return LinearLeastSquares.linearLeastSquares(points).line().slope();
  }
}
