package com.example.demo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class Pagination {
    private int listSize = 10;
    private long page;
    private long totalSize;
    private boolean prev;
    private boolean next;
    public void pageInfo(long page, long totalSize){
        this.page = page;
        this.totalSize = totalSize;
        if(page == 1){
            prev = false;
            if(totalSize <= listSize) next = false;
            else next = true;
        }
        else{
            prev = true;
            if(page * listSize >= totalSize){
                next = false;
            }
            else next = true;
        }
    }
}
