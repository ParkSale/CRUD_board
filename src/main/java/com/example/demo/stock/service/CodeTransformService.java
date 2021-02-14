package com.example.demo.stock.service;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class CodeTransformService {
    private HashMap<String,String> map = new HashMap<>();
    private HashMap<String,String> reverseMap = new HashMap<>();

    @PostConstruct
    private void mapInit() throws IOException {
        Resource resource = new ClassPathResource("gg.txt");
        String filePath = resource.getURI().getPath().substring(1);
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path);
        for(String line : lines){
            String company = line.split("\t")[0];
            String code = line.split("\t")[1];
            map.put(company, code);
            reverseMap.put(code, company);
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
