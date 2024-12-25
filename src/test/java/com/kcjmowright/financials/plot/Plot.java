package com.kcjmowright.financials.plot;

import static com.kcjmowright.financials.config.MathConfig.MATH_CONTEXT;

import java.awt.*;

import javax.swing.*;

import java.awt.geom.*;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.kcjmowright.financials.config.DateTimeConfig;
import com.kcjmowright.financials.sevenpoint.company.Quote;
import com.kcjmowright.financials.util.Dates;
import com.opencsv.CSVReader;

import lombok.SneakyThrows;

public class Plot extends JPanel {
  List<Quote> quotes = loadData();
  BigDecimal margin = BigDecimal.valueOf(60);

  Line2D.Double getLine(BigDecimal x1, BigDecimal y1, BigDecimal x2, BigDecimal y2) {
    return new Line2D.Double(x1.doubleValue(), y1.doubleValue(), x2.doubleValue(), y2.doubleValue());
  }

  protected void paintComponent(Graphics grf) {
    super.paintComponent(grf);
    Graphics2D graph = (Graphics2D) grf;
    graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    BigDecimal width = BigDecimal.valueOf(getWidth());
    BigDecimal height = BigDecimal.valueOf(getHeight());

    graph.setFont(new Font ("Arial", Font.BOLD | Font.ITALIC, 10));

    // Left
    graph.draw(getLine(margin, margin, margin, height.subtract(margin)));
    BigDecimal markX1 = margin.subtract(BigDecimal.TWO);
    BigDecimal markX2 = margin.add(BigDecimal.TWO);
    int markMax = height.subtract(margin.multiply(BigDecimal.TWO)).divide(BigDecimal.TEN, MATH_CONTEXT).intValue();
    for (int i = markMax; --i >= 0;) {
      BigDecimal markY = BigDecimal.valueOf(i).multiply(BigDecimal.TEN).add(margin);
      graph.draw(getLine(markX1, markY, markX2, markY));
      graph.drawString(String.valueOf((markMax - i) * 10), margin.subtract(BigDecimal.valueOf(30)).intValue(), markY.add(BigDecimal.valueOf(5)).intValue());
    }

    // Bottom
    graph.draw(getLine(margin, height.subtract(margin), width.subtract(margin), height.subtract(margin)));

    BigDecimal x = width.subtract(margin.multiply(BigDecimal.TWO)).divide(BigDecimal.valueOf(quotes.size()), MATH_CONTEXT);
    BigDecimal scale = height.subtract(BigDecimal.TWO.multiply(margin)).divide(getMax(quotes), MATH_CONTEXT);

    for (int i = 0; i < quotes.size(); i++) {
      Quote quote = quotes.get(i);
      BigDecimal x1 = margin.add(BigDecimal.valueOf(i).multiply(x));
      BigDecimal open = height.subtract(margin).subtract(scale.multiply(quote.getOpen()));
      BigDecimal close = height.subtract(margin).subtract(scale.multiply(quote.getClose()));
      BigDecimal low = height.subtract(margin).subtract(scale.multiply(quote.getLow()));
      BigDecimal high = height.subtract(margin).subtract(scale.multiply(quote.getHigh()));
      graph.setPaint((open.compareTo(close) >= 0) ? Color.BLACK : Color.RED);

      double xVal = x1.doubleValue() + 2.0;
      graph.draw(new Line2D.Double(xVal, high.doubleValue(), xVal, low.doubleValue()));

      graph.fill(new Rectangle(x1.intValue(), open.max(close).intValue(), 5, open.subtract(close).abs().intValue()));
    }
  }

  private BigDecimal getMax(List<Quote> quotes) {
    return quotes.stream().map(Quote::getClose).max(BigDecimal::compareTo).orElseThrow();
  }

  @SneakyThrows
  public static List<Quote> loadData() {
    List<Quote> quotes = new ArrayList<>();
    Resource dataResource = new ClassPathResource("daily_QQQ.csv");
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(dataResource.getInputStream()))) {
      List<String[]> values = csvReader.readAll();
      var data = values.subList(1, values.size());
      data.forEach(row -> {
        var quote = new Quote();
        quote.setSymbol("QQQ");
        quote.setTimestamp(Dates.toDate(row[0]));
        quote.setOpen(new BigDecimal(row[1]));
        quote.setHigh(new BigDecimal(row[2]));
        quote.setLow(new BigDecimal(row[3]));
        quote.setClose(new BigDecimal(row[4]));
        quote.setVolume(Long.parseLong(row[5]));
        quotes.add(quote);
      });
    }
    quotes.sort(Comparator.comparing(Quote::getTimestamp)); // sort ascending
    //return quotes;
    return quotes.subList(quotes.size() - (52 * 5), quotes.size());
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Plot plot = new Plot();
    frame.add(plot);
    frame.setSize(1920, 1080);
    //frame.setSize(600, 400);
    frame.setLocation(0, 0);
    frame.setVisible(true);
  }


}
