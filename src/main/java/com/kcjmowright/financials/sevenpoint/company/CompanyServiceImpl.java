package com.kcjmowright.financials.sevenpoint.company;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kcjmowright.financials.alphavantage.AlphavantageRestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

  private final CompanyRepository companyRepository;
  private AlphavantageRestClient client = new AlphavantageRestClient();
  @Value("${ALPHAVANTAGE_KEY:null}")
  private String apiKey;

  public Company getCompanyBySymbol(String symbol) {
    return companyRepository.getCompanyBySymbol(symbol);
  }

  public void loadCompany(String symbol) {
    log.info("Fetching company info for {}", symbol);
    Map<String, String> result = client.getCompanyOverview(symbol, this.apiKey);
    Company company = new Company();
    company.setSymbol(symbol);
    company.setName(result.get("Name"));
    company.setDescription(result.get("Description"));
    company.setSector(result.get("Sector"));
    company.setIndustry(result.get("Industry"));
    company.setExchange(result.get("Exchange"));
    log.info("Saving company info for {}", company);
    companyRepository.save(company);
  }

  public void deleteBySymbol(String symbol) {
    companyRepository.delete(companyRepository.getCompanyBySymbol(symbol));
  }
}
