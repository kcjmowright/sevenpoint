# Zero DTE Strategy

## Put Credit Spread (PCS) Strategy: a Bullish Strategy

Using a 0 DTE put options spread

### Entry

* Short (Sell/Bid) ~ -.40 delta put option
* Long (Buy/Ask) ~ -.10 delta put option
* Enter 15 minutes after the opening bell (8:45 AM CST)

### Exit

* if 95% profit of credit is met
* or if 80% stop loss of credit
* otherwise exit 5 minutes before closing bell (2:55 PM CST) no matter what.

### Example

https://trade.thinkorswim.com/trade?symbol=QQQ&leg1=-1,.QQQ250131P510&leg2=1,.QQQ250131P520&orderType=LIMIT&limitPrice=1.82&orderTif=DAY

* Buy 1 Jan 31 510 put ASK (long) .QQQ250131P510
* Sell 1 Jan 31 520 put BID (short) .QQQ250131P520

## Call Credit Spread (CCS) Strategy: A Bearish Strategy

Using a 0 DTE call options spread

### Entry

* Short (Sell/Bid) ~ .40 delta call option
* Long (Buy/Ask) ~ .10 delta call option
* Enter 15 minutes after the bell (8:45 AM CST)

### Exit

* if 95% profit of credit is met
* or if 80% stop loss of credit
* otherwise exit 5 minutes before closing bell (2:55 PM CST) no matter what.

### Example

https://trade.thinkorswim.com/trade?symbol=QQQ&leg1=-1,.QQQ250131C524&leg2=1,.QQQ250131C534&orderType=LIMIT&limitPrice=1.78&orderTif=DAY

* Buy 1 Jan 31 534 call ASK (long) .QQQ250131C534
* Sell 1 Jan 31 524 call BID (short) .QQQ250131C524

## Job

1. Get option quotes.
2. At 15 minutes after the opening bell, place order for a 0 DTE PCS and a 0 DTE CCS, an iron condor.
3. Wait for confirmation.
4. Record assets.
5. Check sell conditions every minute.
6. 5 minutes before the market close, sell any remaining opening positions.


## References

* https://docs.spring.io/spring-batch/reference/step/tasklet.html
* https://docs.spring.io/spring-batch/reference/step/chunk-oriented-processing.html
* https://docs.spring.io/spring-batch/reference/job/running.html
* https://docs.spring.io/spring-framework/reference/data-access/transaction.html
* https://www.baeldung.com/spring-boot-spring-batch
* https://github.com/eugenp/tutorials/blob/master/spring-batch/src/main/java/com/baeldung/batchreaderproperties/BatchConfiguration.java
* https://www.google.com/search?q=using+spring+batch+to+call+a+REST+service&num=10&sca_esv=6bd3bd34b368c660&rlz=1C5GCEM_enUS1105US1105&udm=7&biw=1728&bih=959&sxsrf=AHTn8zoeuxhRNSWzUieaLVJqUPQBvl8Y0g%3A1739323425367&ei=IfirZ9OSFu6optQP29a4gA0&ved=0ahUKEwiTp6zX_LyLAxVulIkEHVsrDtAQ4dUDCBA&uact=5&oq=using+spring+batch+to+call+a+REST+service&gs_lp=EhZnd3Mtd2l6LW1vZGVsZXNzLXZpZGVvIil1c2luZyBzcHJpbmcgYmF0Y2ggdG8gY2FsbCBhIFJFU1Qgc2VydmljZTIIECEYoAEYwwQyCBAhGKABGMMEMggQIRigARjDBEjnJlCIA1ixG3ACeACQAQCYAXSgAYgEqgEDMi4zuAEDyAEA-AEBmAIHoAKdBMICBRAAGO8FwgIIEAAYogQYiQXCAggQABiABBiiBMICChAhGKABGMMEGAqYAwCIBgGSBwM0LjOgB5QY&sclient=gws-wiz-modeless-video#fpstate=ive&vld=cid:58462424,vid:Xjo7cfmB8uE,st:0 
