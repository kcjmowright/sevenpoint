package com.kcjmowright.financials.sevenpoint.company;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

  List<Quote> findBySymbolAndTimestampBetween(String symbol, LocalDateTime start, LocalDateTime end);

  void deleteBySymbol(String symbol);
}
