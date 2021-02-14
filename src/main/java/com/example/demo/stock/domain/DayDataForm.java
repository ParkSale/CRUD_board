package com.example.demo.stock.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class DayDataForm {
    private String date;
    private String startCost;
    private String highCost;
    private String lowCost;
    private String endCost;
    private String volume;
}
