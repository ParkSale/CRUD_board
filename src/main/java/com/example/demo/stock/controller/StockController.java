package com.example.demo.stock.controller;

import com.example.demo.stock.domain.DayDataForm;
import com.example.demo.stock.domain.StockForm;
import com.example.demo.stock.service.CodeTransformService;
import com.example.demo.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class StockController {
    private final StockService stockService;
    private final CodeTransformService codeTransformService;
    @GetMapping("/stock")
    public String stockMain(Model model, @RequestParam(value = "code",defaultValue = "005930") String code) throws IOException {
        StockForm stockForm = stockService.findInfo(code);
        model.addAttribute("form",stockForm);
        return "stock/main";
    }

    @GetMapping("/stock/companyAutoComplete")
    @ResponseBody
    public List<String> companyAutoComplete(@RequestParam("term") String company){
        List<String> ret = codeTransformService.findContain(company);
        return ret;
    }

    @GetMapping("/stock/companyCode")
    @ResponseBody
    public String getCode(String company){
        return codeTransformService.getCodeNumber(company);
    }

    @GetMapping("/stock/dayData")
    @ResponseBody
    public Map<String, Object> getDayDate(@RequestParam(value = "page") int page,  @RequestParam(value="code") String code){
        Map<String,Object> map = new HashMap<>();
        List<DayDataForm> dayDataForms = stockService.getDayData(code, page);
        map.put("data",dayDataForms);
        return map;
    }
}
