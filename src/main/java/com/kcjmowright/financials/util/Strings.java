package com.kcjmowright.financials.util;

public class Strings {
    public static final boolean emptyOrNull(String value) {
        return value == null || "".equalsIgnoreCase(value);
    }
}