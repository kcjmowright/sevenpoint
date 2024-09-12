package com.kcjmowright.financials.sevenpoint.patterns;

/**
 * A Marubozu Candle is a Long Candle which is all body, having no shadows / wicks.
 *
 * <a href="https://www.investopedia.com/terms/m/marubozo.asp#:~:text=A%20Marubozo%20is%20a%20long,are%20used%20by%20technical%20traders.">Marubuzo: What it
 *  Means, How it Works, Why it's Used</a>
 *
 * H - L = ABS(O - C) AND H - L > 3 * AVG(ABS(O - C), 15) / 2
 */
public class Marubozo {
}
