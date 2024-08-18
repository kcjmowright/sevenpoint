package com.kcjmowright.financials.sevenpoint.company;

public interface CompanyService {

  Company getCompanyBySymbol(String symbol);

  void loadCompany(String symbol);

  void deleteBySymbol(String symbol);
}
