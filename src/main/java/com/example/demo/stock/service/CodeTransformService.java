package com.example.demo.stock.service;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Service
public class CodeTransformService {
    private HashMap<String,String> map = new HashMap<>();
    private HashMap<String,String> reverseMap = new HashMap<>();
    private String filePath = "src/main/resources/gg.xlsx";

    @PostConstruct
    private void mapInit() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);

        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);

        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowItr = sheet.iterator();
        while(rowItr.hasNext()){
            Row row = rowItr.next();
            if(row.getRowNum() == 0){
                continue;
            }
            Iterator<Cell> cellItr = row.cellIterator();

            Cell companyNameCell = cellItr.next();
            Cell codeNumberCell = cellItr.next();

            map.put(companyNameCell.getStringCellValue(), codeNumberCell.getStringCellValue());
            reverseMap.put(codeNumberCell.getStringCellValue(), companyNameCell.getStringCellValue());
        }
    }

    public String getCompanyName(String code){
        return reverseMap.get(code);
    }
    public String getCodeNumber(String name){
        return map.get(name);
    }

    public List<String> findContain(String company) {
        List<String> ret = new ArrayList<>();
        for(Map.Entry<String,String> elem : map.entrySet()){
            if(elem.getKey().contains(company)){
                ret.add(elem.getKey());
            }
        }
        return ret;
    }
}
