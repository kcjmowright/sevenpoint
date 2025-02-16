package com.kcjmowright.financials.sevenpoint.schwab;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.kcjmowright.schwab.marketdata.api.OptionChainsApi;
import com.kcjmowright.schwab.marketdata.api.QuotesApi;
import com.kcjmowright.schwab.trader.api.AccountsApi;
import com.kcjmowright.schwab.trader.api.OrdersApi;

@Configuration
public class SchwabClientConfiguration {

  @Value("${batch.schwab.apikey}")
  String apiKey;

  @Bean(name = "marketDataApiClient")
  public com.kcjmowright.schwab.marketdata.invoker.ApiClient marketDataApiClient() {
    com.kcjmowright.schwab.marketdata.invoker.ApiClient apiClient = new com.kcjmowright.schwab.marketdata.invoker.ApiClient();
    // apiClient.setApiKey(apiKey);
    return apiClient;
  }

  @Bean
  public QuotesApi quotesApi(com.kcjmowright.schwab.marketdata.invoker.ApiClient marketDataApiClient) {
    return new QuotesApi(marketDataApiClient);
  }

  @Bean
  public OptionChainsApi optionChainsApi(com.kcjmowright.schwab.marketdata.invoker.ApiClient marketDataApiClient) {
    return new OptionChainsApi(marketDataApiClient);
  }

  @Bean(name = "traderApiClient")
  public com.kcjmowright.schwab.trader.invoker.ApiClient traderApiClient() {
    com.kcjmowright.schwab.trader.invoker.ApiClient apiClient = new com.kcjmowright.schwab.trader.invoker.ApiClient();
    // apiClient.setApiKey(apiKey);
    return apiClient;
  }

  @Bean
  public OrdersApi ordersApi(com.kcjmowright.schwab.trader.invoker.ApiClient traderApiClient) {
    return new OrdersApi(traderApiClient);
  }

  @Bean
  public AccountsApi accountsApi(com.kcjmowright.schwab.trader.invoker.ApiClient traderApiClient) {
    return new AccountsApi(traderApiClient);
  }

}
