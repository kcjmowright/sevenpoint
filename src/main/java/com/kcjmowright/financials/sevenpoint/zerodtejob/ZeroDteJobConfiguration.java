package com.kcjmowright.financials.sevenpoint.zerodtejob;

import java.time.LocalDate;
import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.VirtualThreadTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import com.kcjmowright.schwab.marketdata.api.OptionChainsApi;
import com.kcjmowright.schwab.marketdata.model.OptionChain;
import com.kcjmowright.schwab.marketdata.model.OptionContract;
import com.kcjmowright.schwab.trader.api.OrdersApi;
import com.kcjmowright.schwab.trader.model.AccountOption;
import com.kcjmowright.schwab.trader.model.AccountsBaseInstrument;
import com.kcjmowright.schwab.trader.model.Duration;
import com.kcjmowright.schwab.trader.model.Instruction;
import com.kcjmowright.schwab.trader.model.OrderLegCollection;
import com.kcjmowright.schwab.trader.model.OrderRequest;
import com.kcjmowright.schwab.trader.model.OrderStrategyType;
import com.kcjmowright.schwab.trader.model.OrderTypeRequest;
import com.kcjmowright.schwab.trader.model.Session;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// https://www.baeldung.com/spring-boot-spring-batch
// https://github.com/eugenp/tutorials/blob/master/spring-batch/src/main/java/com/baeldung/bootbatch/BatchConfiguration.java
@Configuration
@Slf4j
@RequiredArgsConstructor
public class ZeroDteJobConfiguration {

  @Value("symbol:QQQ")
  private String symbol;
  private final OrdersApi ordersApi;
  private final OptionChainsApi optionChainsApi;

  @Bean
  public VirtualThreadTaskExecutor taskExecutor() {
    return new VirtualThreadTaskExecutor("ZeroDteJob");
  }

  @Bean("optionEntryJob")
  public Job optionEntryJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step optionOrderStep) {
    return new JobBuilder("optionEntryJob", jobRepository)
        .incrementer(new RunIdIncrementer())
        .listener(listener)
        .flow(optionOrderStep)
        .end()
        .build();
  }

  @Bean
  @StepScope
  public Step optionOrderStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
    return new StepBuilder("", jobRepository).tasklet((contribution, chunkContext) -> {
      LocalDate today = LocalDate.now();
      OptionChain optionChain = optionChainsApi.getChain(symbol, "ALL", 60, true, null, null, null, null,
          today, today, null, null, null, 0, null, null, null);
      log.info("{}", optionChain);
      List<OptionContract> callContracts = optionChain.getCallExpDateMap().values().iterator().next().values().stream()
          .filter(contract -> {
            double delta = contract.getDelta();
            return (delta > 0.07 && delta < 0.14) || (delta > 0.37 && delta < 0.44);
          }).toList();
      log.info("Call Contracts: {}", callContracts);
      OptionContract longCallContract = optionChain.getCallExpDateMap().values().iterator().next().values().stream()
          .filter(contract -> {
            double delta = contract.getDelta();
            return (delta > 0.07 && delta < 0.14);
          }).findFirst().orElseThrow();
      OptionContract shortCallContract = optionChain.getCallExpDateMap().values().iterator().next().values().stream()
          .filter(contract -> {
            double delta = contract.getDelta();
            return (delta > 0.37 && delta < 0.44);
          }).findFirst().orElseThrow();
      List<OptionContract> putContracts = optionChain.getPutExpDateMap().values().iterator().next().values().stream()
          .filter(contract -> {
            double delta = contract.getDelta();
            return (delta < -0.07 && delta > -0.14) || (delta < -0.37 && delta > -0.44);
          }).toList();
      log.info("Put Contracts: {}", putContracts);
      OptionContract longPutContract = optionChain.getPutExpDateMap().values().iterator().next().values().stream()
          .filter(contract -> {
            double delta = contract.getDelta();
            return (delta < -0.07 && delta > -0.14);
          }).findFirst().orElseThrow();
      OptionContract shortPutContract = optionChain.getPutExpDateMap().values().iterator().next().values().stream()
          .filter(contract -> {
            double delta = contract.getDelta();
            return (delta < -0.37 && delta > -0.44);
          }).findFirst().orElseThrow();


      /*
      {
  "orderType": "NET_DEBIT",
  "session": "NORMAL",
  "price": "0.10",
  "duration": "DAY",
  "orderStrategyType": "SINGLE",
  "orderLegCollection": [
   {
    "instruction": "BUY_TO_OPEN",
    "quantity": 2,
    "instrument": {
     "symbol": "XYZ   240315P00045000",
     "assetType": "OPTION"
    }
   },
   {
    "instruction": "SELL_TO_OPEN",
    "quantity": 2,
    "instrument": {
     "symbol": "XYZ   240315P00043000",
      "assetType": "OPTION"
    }
   }
  ]
}
       */

      double quantity = 1.0;
      double price = 1.0;
      String accountNumber = "";

      OrderRequest orderRequest = new OrderRequest();
      orderRequest.setOrderType(OrderTypeRequest.NET_CREDIT);
      orderRequest.setSession(Session.NORMAL);
      orderRequest.setDuration(Duration.DAY);
      orderRequest.setOrderStrategyType(OrderStrategyType.SINGLE);
      orderRequest.setPrice(price);

      AccountOption longCallInstrument = new AccountOption();
      longCallInstrument.setAssetType(AccountsBaseInstrument.AssetTypeEnum.OPTION);
      longCallInstrument.setSymbol(longCallContract.getSymbol());
      OrderLegCollection longCall = new OrderLegCollection();
      longCall.setInstruction(Instruction.BUY_TO_OPEN);
      longCall.setQuantity(quantity);
      longCall.setInstrument(longCallInstrument);

      AccountOption shortCallInstrument = new AccountOption();
      shortCallInstrument.setAssetType(AccountsBaseInstrument.AssetTypeEnum.OPTION);
      shortCallInstrument.setSymbol(shortCallContract.getSymbol());
      OrderLegCollection shortCall = new OrderLegCollection();
      shortCall.setInstruction(Instruction.SELL_TO_OPEN);
      shortCall.setQuantity(quantity);
      shortCall.setInstrument(shortCallInstrument);

      AccountOption longPutInstrument = new AccountOption();
      longPutInstrument.setAssetType(AccountsBaseInstrument.AssetTypeEnum.OPTION);
      longPutInstrument.setSymbol(longPutContract.getSymbol());
      OrderLegCollection longPut = new OrderLegCollection();
      longPut.setInstruction(Instruction.BUY_TO_OPEN);
      longPut.setQuantity(quantity);
      longPut.setInstrument(longPutInstrument);

      AccountOption shortPutInstrument = new AccountOption();
      shortPutInstrument.setAssetType(AccountsBaseInstrument.AssetTypeEnum.OPTION);
      shortPutInstrument.setSymbol(shortPutContract.getSymbol());
      OrderLegCollection shortPut = new OrderLegCollection();
      shortPut.setInstruction(Instruction.SELL_TO_OPEN);
      shortPut.setQuantity(quantity);
      shortPut.setInstrument(shortPutInstrument);

      orderRequest.orderLegCollection(List.of(longCall, shortCall, longPut, shortPut));

      ordersApi.placeOrder(orderRequest, accountNumber);
      return RepeatStatus.FINISHED;
    }, platformTransactionManager).build();
  }

}
