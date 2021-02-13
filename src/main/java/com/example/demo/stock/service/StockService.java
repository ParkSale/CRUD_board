package com.example.demo.stock.service;

import com.example.demo.stock.domain.StockForm;
import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class StockService {

    private final CodeTransformService codeTransformService;


    /*public String getDayData(String name){
        String code = codeTransformService.getCodeNumber(name);
        final String url = "http://fchart.stock.naver.com/sise.nhn?symbol=" + code + "&timeframe=day&count=100&requestType=0";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        String str = restTemplate.exchange(url, HttpMethod.GET,entity,String.class).getBody();
        String[] list = str.split("item");
        for(int i=1;i<list.length - 1;++i){
            String[] t = list[i].split("[|]");
            //System.out.println(t[0].substring(7) + " " + t[1] + " " + t[2] + " " + t[3] + " " + t[4] + " " + t[5].substring(0,t[5].length()-14));
        }
        return str;
    }*/

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

}
