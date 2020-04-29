package com.example.demo.controller;

import com.example.demo.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AjaxController {
    private final UsersService usersService;
    @GetMapping("/users/nameChk/{name}")
    public String nameChk(@PathVariable("name") String name){
        if(usersService.findByName(name)!= null){
            return "success";
        }
        return "false";
    }
}
