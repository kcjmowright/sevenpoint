# Sevenpoint

An implementation of A.J. Monte's Seven Point Trading Checklist.

## Spring Info

Using configuration https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.3.2&packaging=jar&jvmVersion=22&groupId=com.example.financials&artifactId=sevenpoint&name=sevenpoint&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.financials.sevenpoint&dependencies=web,data-jpa,lombok,security,flyway,postgresql,spring-ai-vectordb-pgvector,devtools,actuator

## Setup

### Database

```sql
createdb --username postgres -E UTF-8 -T template0 --no-password sevenpoint
```

### Environment Variables

`ALPHAVANTAGE_KEY` - Alphavantage API key.  See https://www.alphavantage.co/support/#api-key

## Reference

### JPA/Spring Data

* https://vladmihalcea.com/date-timestamp-jpa-hibernate/
* https://docs.spring.io/spring-data/jpa/reference/repositories/query-keywords-reference.html
* https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html#jpa.query-methods.query-creation

### SpringFramework

* https://docs.spring.io/spring-framework/docs/current/javadoc-api/index.html
* https://docs.spring.io/spring-framework/reference/

## Market Data APIs

* https://www.alphavantage.co/documentation/  - Free API, just have to apply for an API key.  Sign-up at https://www.alphavantage.co/support/#api-key
* https://sws-gateway.schwab.com/ui/host/ - Charles Schwab investor API.

## Market Data

* https://www.forexfactory.com/ - for professional traders participating in the foreign exchange markets.