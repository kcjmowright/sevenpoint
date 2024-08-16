package com.kcjmowright.financials.sevenpoint.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kcjmowright.financials.sevenpoint.company.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    Company getCompanyBySymbol(String symbol);
}
