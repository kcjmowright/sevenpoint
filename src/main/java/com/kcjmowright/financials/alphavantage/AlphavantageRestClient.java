package com.kcjmowright.financials.alphavantage;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.client.RestClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AlphavantageRestClient {

  RestClient client = RestClient.create();

  /**
   * Retrieves historical daily quotes for the given ticker symbol.
   *
   * @param symbol     ticker symbol.
   * @param apiKey     the API key.
   * @param outputSize the optional {@code OutputSize}.  Default is COMPACT.
   * @param dataType   the optional {@code DataType}.  Default is JSON.
   */
  public Map<String, Map<String, Map>> getTimeSeriesDaily(String symbol, String apiKey, OutputSize outputSize, DataType dataType) {
    OutputSize os = outputSize == null ? OutputSize.COMPACT : outputSize;
    DataType dt = dataType == null ? DataType.JSON : dataType;
    return client.get().uri("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol={symbol}&apikey={apiKey}&outputsize={outputSize}&datatype" +
        "={dataType}", symbol, apiKey, os.getLabel(), dt.getLabel()).retrieve().body(HashMap.class);
  }

  /**
   * Retrieves a company's overview for the given symbol.
   *
   * @param symbol ticker symbol.
   * @param apiKey the API key.
   */
  public Map<String, String> getCompanyOverview(String symbol, String apiKey) {
    return client.get().uri("https://www.alphavantage.co/query?function=OVERVIEW&symbol={symbol}&apikey={apiKey}", symbol, apiKey).retrieve().body(HashMap.class);
  }

  public static void main(String[] args) {
    AlphavantageRestClient client = new AlphavantageRestClient();
    log.info(client.getTimeSeriesDaily(args[0], args[1], OutputSize.valueOf(args[2]), DataType.valueOf(args[3])).toString());
    log.info(client.getCompanyOverview(args[0], args[1]).toString());
  }
}
