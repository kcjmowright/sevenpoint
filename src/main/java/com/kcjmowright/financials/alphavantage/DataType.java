package com.kcjmowright.financials.alphavantage;

public enum DataType {
  JSON("json"),

  CSV("csv");

  private String label;

  DataType(String label) {
    this.label = label;
  }

  public String getLabel() {
    return this.label;
  }
}
