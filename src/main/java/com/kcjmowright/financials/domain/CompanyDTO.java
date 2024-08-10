package com.kcjmowright.financials.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyDTO {
    private String ticker;
    private String name;
    private String sector;
    private String industry;
    private String exchange;
}
