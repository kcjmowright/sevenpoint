package com.kcjmowright.financials.alphavantage;

public enum OutputSize {
    /**
     *  The latest 100 data points.
     */
    COMPACT("compact"),

    /**
     * The full-length time series of 20+ years of historical data.
     */
    FULL("full");

    private String label;

    OutputSize(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
