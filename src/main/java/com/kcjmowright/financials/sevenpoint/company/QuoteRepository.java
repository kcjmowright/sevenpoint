package com.kcjmowright.financials.sevenpoint.company;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kcjmowright.financials.sevenpoint.company.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    public List<Quote> findBySymbolAndMarkBetween(String symbol, LocalDateTime start, LocalDateTime end);

    public void deleteBySymbol(String symbol);
}
