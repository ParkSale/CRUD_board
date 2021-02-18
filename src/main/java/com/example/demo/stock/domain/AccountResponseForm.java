package com.example.demo.stock.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AccountResponseForm {
    private String api_tran_id;
    private String rsp_code;
    private String rsp_message;
    private String api_tran_dtm;
    private String user_name;
    private int res_cnt;
    private AccountForm[] res_list;
}
