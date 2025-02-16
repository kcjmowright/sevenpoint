package com.kcjmowright.financials.sevenpoint.indicators;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import com.kcjmowright.financials.sevenpoint.math.LinearLeastSquares;
import com.kcjmowright.financials.sevenpoint.math.Point;
import com.kcjmowright.financials.sevenpoint.company.Quote;

public class Trend {

  public static BigDecimal findPriceSlope(List<Quote> q, int periodSize) {
    final List<Quote> sublist = q.size() <= periodSize ? q : q.subList(q.size() - periodSize, q.size());
    final List<Point> points = Stream.concat(
        IntStream.range(0, sublist.size()).mapToObj(i -> new Point(BigDecimal.valueOf(i), sublist.get(i).getOpen())),
        IntStream.range(0, sublist.size()).mapToObj(i -> new Point(BigDecimal.valueOf(i), sublist.get(i).getClose()))
    ).toList();
    return LinearLeastSquares.linearLeastSquares(points).line().slope();
  }

  public static BigDecimal findVolumeSlope(List<Quote> q, int periodSize) {
    final List<Quote> sublist = q.size() <= periodSize ? q
        : q.subList(q.size() - periodSize, q.size());
    final List<Point> points = IntStream.range(0, sublist.size())
        .mapToObj(i -> new Point(BigDecimal.valueOf(i), BigDecimal.valueOf(sublist.get(i).getVolume()))).toList();
    return LinearLeastSquares.linearLeastSquares(points).line().slope();
  }
}
