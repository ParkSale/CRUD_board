package com.example.demo.stock.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class SellStockForm {
    private String fintech_use_num;
    private String name;
    private Long price;
    private Long count;
}
