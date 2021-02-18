package com.example.demo.stock.service;

import com.example.demo.stock.domain.*;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StockService {

    private final CodeTransformService codeTransformService;


    public List<DayDataForm> getDayData(String code, int page){
        final String url = "http://fchart.stock.naver.com/sise.nhn?symbol=" + code + "&timeframe=day&count=100&requestType=0";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        String str = restTemplate.exchange(url, HttpMethod.GET,entity,String.class).getBody();
        String[] arr = str.split("item");
        ArrayList<String> list = new ArrayList<>();
        for(String s : arr) list.add(s);
        List<DayDataForm> ret = new ArrayList<>();
        Collections.reverse(list);
        for(int i=1 + 10*(page-1);i<1 + 10*(page-1) + 10;++i){
            String[] t = list.get(i).split("[|]");
            DayDataForm dayDataForm = DayDataForm.builder()
                                        .date(t[0].substring(7))
                                        .startCost(t[1]).highCost(t[2]).lowCost(t[3]).endCost(t[4]).volume(t[5].substring(0,t[5].length()-14)).build();
            ret.add(dayDataForm);

        }
        Collections.reverse(ret);
        return ret;
    }

    public StockForm findInfo(String code) throws IOException {
        return makeStockForm(code);
    }

    private StockForm makeStockForm(String code) throws IOException {
        StockForm stockForm = new StockForm();
        stockForm.setCompanyName(codeTransformService.getCompanyName(code));

        String url = "https://finance.naver.com/item/main.nhn?code=" + code;
        Document document = Jsoup.connect(url).get();
        Element nowCostElement = document.select("#chart_area > div.rate_info > div > p.no_today").get(0);
        stockForm.setNowCost(nowCostElement.getElementsByClass("blind").get(0).text());
        Element prevDayElement = document.select("#chart_area > div.rate_info > table > tbody > tr:nth-child(1) > td.first").get(0);
        stockForm.setPrevDay(prevDayElement.getElementsByClass("blind").get(0).text());
        Element highCostElement = document.select("#chart_area > div.rate_info > table > tbody > tr:nth-child(1) > td:nth-child(2)").get(0);
        stockForm.setHighCost(highCostElement.getElementsByClass("blind").get(0).text());
        Element startCostElement = document.select("#chart_area > div.rate_info > table > tbody > tr:nth-child(2) > td.first").get(0);
        stockForm.setStartCost(startCostElement.getElementsByClass("blind").get(0).text());
        Element lowCostElement = document.select("#chart_area > div.rate_info > table > tbody > tr:nth-child(2) > td:nth-child(2)").get(0);
        stockForm.setLowCost(lowCostElement.getElementsByClass("blind").get(0).text());
        return stockForm;
    }

    public AccountForm[] getAccountList() {
        String url = "https://developers.kftc.or.kr/proxy/account/list";
        String seqNo = "1100759890";
        String include_cancel_yn="Y";
        String sort_order = "D";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzU5ODkwIiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjEyNTE1NTYsImp0aSI6IjFkODVjNTJjLTA3MDMtNDgzNS1iYzYwLTNhNjQ0OTQ2NmFhNyJ9.wIVG_HCtq-5M9ZIpoUq42y4fVg3C0BugaPzWrp8WI30");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("user_seq_no",seqNo).queryParam("include_cancel_yn",include_cancel_yn).queryParam("sort_order",sort_order);
        HttpEntity<AccountResponseForm> entity = new HttpEntity<>(httpHeaders);
        AccountResponseForm accountResponseForm = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,entity,AccountResponseForm.class).getBody();
        return accountResponseForm.getRes_list();
    }

    private int getRandomNumber(){
        Random rand = new Random();
        String rst = Integer.toString(rand.nextInt(8) + 1);
        for(int i=0; i < 8; i++){
            rst += Integer.toString(rand.nextInt(9));
        }
        return Integer.parseInt(rst);
    }
    public BalanceForm getBalance(String fintech_use_num) {
        String url = "https://testapi.openbanking.or.kr/v2.0/account/balance/fin_num";
        String bank_tran_id = "T991637050U" + getRandomNumber();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
        String tran_dtime = format.format(System.currentTimeMillis());
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization","Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzU5ODkwIiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjEyNTE1NTYsImp0aSI6IjFkODVjNTJjLTA3MDMtNDgzNS1iYzYwLTNhNjQ0OTQ2NmFhNyJ9.wIVG_HCtq-5M9ZIpoUq42y4fVg3C0BugaPzWrp8WI30");
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("bank_tran_id",bank_tran_id).queryParam("fintech_use_num",fintech_use_num).queryParam("tran_dtime",tran_dtime);
        HttpEntity<BalanceForm> entity = new HttpEntity<>(httpHeaders);
        BalanceForm balanceForm = restTemplate.exchange(builder.toUriString(), HttpMethod.GET,entity,BalanceForm.class).getBody();
        return balanceForm;
    }
}
