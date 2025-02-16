package com.kcjmowright.financials.sevenpoint.portfolio;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class Portfolio {

  public List<Asset> assets = new ArrayList<>();

}
