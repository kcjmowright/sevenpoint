package com.kcjmowright.financials.sevenpoint.zerodtejob;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class ZeroDTEEntryJobRunner {

  private final JobLauncher jobLauncher;
  private final Job optionEntryJob;

  @Scheduled(cron = "${batch.zerodte.cron}", zone = "GMT")
  public void runJob() {
    ZonedDateTime now = ZonedDateTime.now(ZoneOffset.UTC);
    launchJob(now);
  }

  void launchJob(ZonedDateTime triggerZonedDateTime) {
    try {
      JobParameters jobParameters = new JobParametersBuilder()
          .addString("TRIGGERED_DATE_TIME", triggerZonedDateTime.toString())
          .toJobParameters();
      jobLauncher.run(optionEntryJob, jobParameters);
    } catch (Exception e) {
      log.error("Failed to run", e);
    }
  }
}
