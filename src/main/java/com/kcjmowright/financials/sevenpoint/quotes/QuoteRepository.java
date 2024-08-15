package com.kcjmowright.financials.sevenpoint.quotes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kcjmowright.financials.sevenpoint.quotes.Quote;

@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long> {

    public List<Quote> findByTickerAndMarkBetween(String ticker, LocalDateTime start, LocalDateTime end);

}
