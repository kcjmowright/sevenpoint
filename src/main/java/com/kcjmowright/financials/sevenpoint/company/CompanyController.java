package com.kcjmowright.financials.sevenpoint.company;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/company")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

  private final CompanyService companyService;

  @GetMapping("/{symbol}")
  public Company getCompanyBySymbol(@PathVariable(name = "symbol") String symbol) {
    return companyService.getCompanyBySymbol(symbol);
  }

  @PutMapping("/{symbol}")
  public void putCompanyBySymbol(@PathVariable(name = "symbol") String symbol) {
    companyService.loadCompany(symbol);
  }

  @DeleteMapping("/{symbol}")
  public void deleteCompanyBySymbol(@PathVariable(name = "symbol") String symbol) {
    companyService.deleteBySymbol(symbol);
  }
}
