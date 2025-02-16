package com.kcjmowright.financials.sevenpoint.util;

public class Strings {
  public static boolean emptyOrNull(String value) {
    return value == null || "".equalsIgnoreCase(value);
  }
}
