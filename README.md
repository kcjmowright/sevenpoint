# Sevenpoint

An implementation of A.J. Monte's Seven Point Trading Checklist.

## Seven Points

### Type of Candle

Bearish or bullish.

### Volume

Up or down compared to the trend.

### Moving Average Divergence

A wide divergence from the 20-day simple moving average.

### Gaps

Gap above or below the market.  Recently closed gaps.

### Stochastics

Overbought or oversold.
Wide divergence from the k and d.

### CCI

Overbought or oversold.
Buy or sell signal
Cross above or below the zero line.

### Role Reversal

A bounce or breakout of the role reversal.
Divergence above or below the role reversal.

## Spring Info

Using configuration https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.3.2&packaging=jar&jvmVersion=22&groupId=com.example.financials&artifactId=sevenpoint&name=sevenpoint&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.financials.sevenpoint&dependencies=web,data-jpa,lombok,security,flyway,postgresql,spring-ai-vectordb-pgvector,devtools,actuator

## Setup

### Database

```sql
createdb --username postgres -E UTF-8 -T template0 --no-password sevenpoint
```

### Environment Variables

`ALPHAVANTAGE_KEY` - Alphavantage API key.  Create a key by visiting https://www.alphavantage.co/support/#api-key

## Reference

### JPA/Spring Data

* https://vladmihalcea.com/date-timestamp-jpa-hibernate/
* https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html
* https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.query-creation

### SpringFramework

* https://docs.spring.io/spring-framework/docs/current/javadoc-api/index.html
* https://docs.spring.io/spring-framework/reference/

### Spring Batch

* https://www.baeldung.com/spring-boot-spring-batch
* https://github.com/eugenp/tutorials

## Market Data APIs

### Alphaadvantage

https://www.alphavantage.co/documentation/  - Free API, just have to apply for an API key.  Sign-up at https://www.alphavantage.co/support/#api-key

### Schwab

#### Schwab Developer References

* https://developer.schwab.com/
* https://sws-gateway.schwab.com/ui/host/ - Charles Schwab investor API.
* https://developer.schwab.com/products/trader-api--individual/details/documentation/Retail%20Trader%20API%20Production


#### Schwab API Swagger Codegen Commands

```shell
java -jar swagger-codegen-cli-3.0.67.jar generate \
-i market-data-openapi-schema.json \
-l java \
--api-package com.kcjmowright.schwab.marketdata.api \
--model-package com.kcjmowright.schwab.marketdata.model \
--invoker-package com.kcjmowright.schwab.marketdata.invoker \
--group-id com.kcjmowright.schwab \
--artifact-id schwab-marketdata-client \
--artifact-version 0.0.1 \
--library resttemplate \
-o schwab-marketdata-client \
-c config.json
```

```shell
java -jar swagger-codegen-cli-3.0.67.jar generate \
-i trader-openapi-schema.json \
-l java \
--api-package com.kcjmowright.schwab.trader.api \
--model-package com.kcjmowright.schwab.trader.model \
--invoker-package com.kcjmowright.schwab.trader.invoker \
--group-id com.kcjmowright.schwab \
--artifact-id schwab-trader-client \
--artifact-version 0.0.1 \
--library resttemplate \
-o schwab-trader-client \
-c config.json
```

## Market Data

* https://www.forexfactory.com/ - for professional traders participating in the foreign exchange markets.

