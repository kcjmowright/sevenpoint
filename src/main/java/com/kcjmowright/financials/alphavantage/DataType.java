package com.kcjmowright.financials.alphavantage;

import lombok.Getter;

@Getter
public enum DataType {
  JSON("json"),

  CSV("csv");

  private final String label;

  DataType(String label) {
    this.label = label;
  }
}
