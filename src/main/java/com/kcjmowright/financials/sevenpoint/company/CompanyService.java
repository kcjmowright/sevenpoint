package com.kcjmowright.financials.sevenpoint.company;

import java.time.LocalDateTime;
import java.util.List;

public interface CompanyService {

    Company getCompanyBySymbol(String symbol);

    void loadCompany(String symbol);
}
