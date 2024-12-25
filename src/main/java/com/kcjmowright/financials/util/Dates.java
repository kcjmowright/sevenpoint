package com.kcjmowright.financials.util;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.kcjmowright.financials.config.DateTimeConfig;

public class Dates {

  public static LocalDateTime toDate(String input) {
    return LocalDate.parse(input, DateTimeConfig.dateFormatter).atStartOfDay();
  }
}
