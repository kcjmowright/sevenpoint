package com.kcjmowright.financials.sevenpoint.alphavantage;

import lombok.Getter;

@Getter
public enum OutputSize {
  /**
   * The latest 100 data points.
   */
  COMPACT("compact"),

  /**
   * The full-length time series of 20+ years of historical data.
   */
  FULL("full");

  private final String label;

  OutputSize(String label) {
    this.label = label;
  }
}
