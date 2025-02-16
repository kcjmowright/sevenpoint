package com.kcjmowright.financials.sevenpoint.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.kcjmowright.financials.sevenpoint.config.DateTimeConfig;

public class Dates {

  public static LocalDateTime toDate(String input) {
    return LocalDate.parse(input, DateTimeConfig.dateFormatter).atStartOfDay();
  }
}
