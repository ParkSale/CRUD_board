package com.example.demo.stock.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BuyStockForm {
    private String fintech_use_num;
    private String code;
    private Long price;
    private Long count;
}
