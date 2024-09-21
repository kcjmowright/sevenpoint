package com.kcjmowright.financials.sevenpoint.indicators;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.Getter;

/**
 *
 */
@Getter
public class PriceGaps {

  private final List<Quote> quotes;
  private final List<Gap> openedGaps = new ArrayList<>();
  private final Map<LocalDateTime, Gap> closedGaps = new HashMap<>();

  public PriceGaps(List<Quote> quotes) {
    this.quotes = List.copyOf(Objects.requireNonNull(quotes, "Expected quotes"));
    calculate();
  }

  void calculate() {
    Quote last = null;
    for (Quote current: quotes) {
      if (last != null) {
        openedGaps.stream()
            .filter(gap -> (gap.getDirection() > 0 && current.getLow().compareTo(gap.getLow()) <= 0)
                || (gap.getDirection() < 0 && current.getHigh().compareTo(gap.getHigh()) >= 0))
            .findFirst()
            .ifPresent(gap -> this.closeGap(current.getTimestamp(), gap));
        findGap(last, current).ifPresent(openedGaps::add);
      }
      last = current;
    }
  }

  private Optional<Gap> findGap(Quote last, Quote current) {
    if (current.getLow().subtract(last.getHigh()).compareTo(BigDecimal.ZERO) > 0) { // Gap down
      return Optional.of(new Gap(-1, current.getLow(), last.getHigh(), current.getTimestamp()));
    }
    if (last.getLow().subtract(current.getHigh()).compareTo(BigDecimal.ZERO) > 0) { // Gap up
      return Optional.of(new Gap(1, last.getLow(), current.getHigh(), current.getTimestamp()));
    }
    return Optional.empty();
  }

  private void closeGap(LocalDateTime timestamp, Gap gap) {
    closedGaps.put(timestamp, gap);
    openedGaps.remove(gap);
  }
}
