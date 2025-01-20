package com.kcjmowright.financials.sevenpoint.analysis;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.kcjmowright.financials.sevenpoint.company.Quote;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SevenPointImpl implements SevenPoint {
  private final List<Evidence> sevenPoints = List.of(
      new CCIEvidence(),
      new GapEvidence(),
      new MovingAverageEvidence(),
      new RoleReversalEvidence(),
      new StochasticEvidence(),
      new VolumeEvidence(),
      new TypeOfCandleEvidence());

  @SneakyThrows
  public int analyze(List<Quote> quotes) {
    final AtomicInteger evidence = new AtomicInteger();
    sevenPoints.stream().map(e -> Thread.startVirtualThread(() -> evidence.getAndAdd(e.process(quotes)))).forEach(t -> {
      try {
        t.join();
      } catch (Exception e) {
        log.error("{}", e.getMessage(), e);
      }
    });
    return evidence.intValue();
  }
}
