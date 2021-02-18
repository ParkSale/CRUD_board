package com.example.demo.stock.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BalanceForm {
    private String api_tran_id;
    private String api_tran_dtm;
    private String rspcode;
    private String rsp_message;
    private String back_tran_id;
    private String bank_tran_date;
    private String bank_Code_tran;
    private String bank_rsp_code;
    private String bank_rsp_message;
    private String fintech_use_num;
    private String balance_amt;
    private String available_amt;
    private String account_type;
    private String product_name;
}
