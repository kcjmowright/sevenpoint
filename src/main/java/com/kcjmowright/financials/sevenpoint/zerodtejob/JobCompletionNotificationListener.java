package com.kcjmowright.financials.sevenpoint.zerodtejob;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobExecution;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.kcjmowright.financials.sevenpoint.portfolio.AssetType;
import com.kcjmowright.financials.sevenpoint.portfolio.Asset;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class JobCompletionNotificationListener implements JobExecutionListener {
  private final JdbcTemplate jdbcTemplate;

  @Override
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("ZeroDTE job finished");
      String query = "SELECT id, symbol, bought, sold, purchaseprice, sellprice, quantity, type FROM asset";
      jdbcTemplate.query(query, (rs, row) -> new Asset(
          rs.getLong(1),
          rs.getString(2),
          rs.getTimestamp(3).toLocalDateTime(),
          rs.getTimestamp(4).toLocalDateTime(),
          rs.getBigDecimal(5),
          rs.getBigDecimal(6),
          rs.getBigDecimal(7),
          AssetType.valueOf(rs.getString(8)))).forEach(asset -> log.info("Found < {} > in the database.", asset));
    }
  }
}
