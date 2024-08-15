package com.kcjmowright.financials.sevenpoint.quotes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kcjmowright.financials.sevenpoint.quotes.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String> {
    
}
