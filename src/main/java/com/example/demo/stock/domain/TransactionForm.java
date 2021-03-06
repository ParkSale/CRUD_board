package com.example.demo.stock.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TransactionForm {
    private String company;
    private Long number;
    private Long price;
}
