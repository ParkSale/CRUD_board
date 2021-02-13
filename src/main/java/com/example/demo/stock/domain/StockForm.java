package com.example.demo.stock.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockForm {
    String companyName;
    String nowCost;
    String prevDay;
    String startCost;
    String highCost;
    String lowCost;
}
