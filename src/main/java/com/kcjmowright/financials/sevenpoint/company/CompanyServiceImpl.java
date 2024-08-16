package com.kcjmowright.financials.sevenpoint.company;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.kcjmowright.financials.alphavantage.AlphavantageRestClient;
import com.kcjmowright.financials.sevenpoint.company.Company;
import com.kcjmowright.financials.sevenpoint.company.CompanyRepository;
import com.kcjmowright.financials.sevenpoint.company.CompanyService;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

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
        Map<String, String> result = client.getCompanyOverview(symbol, this.apiKey);
        Company company = new Company();
        company.setSymbol(symbol);
        company.setName(result.get("Name"));
        company.setDescription(result.get("Description"));
        company.setSector(result.get("Sector"));
        company.setIndustry(result.get("Industry"));
        company.setExchange(result.get("Exchange"));
        log.info(company.toString());
    }
}
