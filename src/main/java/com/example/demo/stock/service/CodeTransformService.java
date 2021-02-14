package com.example.demo.stock.service;


import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Service
public class CodeTransformService {
    private HashMap<String,String> map = new HashMap<>();
    private HashMap<String,String> reverseMap = new HashMap<>();

    @PostConstruct
    private void mapInit() throws IOException {
        try{
            FileReader fr;
            BufferedReader br;
            String fileName = "/home/ec2-user/app/step2/gg.txt";
            String line;
            fr = new FileReader(fileName);
            br = new BufferedReader(fr);
            while((line = br.readLine()) != null){
                line = line.trim();
                String company = line.split("\t")[0];
                String code = line.split("\t")[1];
                map.put(company, code);
                reverseMap.put(code, company);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        finally{
        }
        /*Resource resource = new ClassPathResource("gg.txt");
        String filePath = resource.getURI().getPath().substring(1);
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String str;
        while((str = bufferedReader.readLine()) != null){
            String company = str.split("\t")[0];
            String code = str.split("\t")[1];
            map.put(company, code);
            reverseMap.put(code, company);
        }*/
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
