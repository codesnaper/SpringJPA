package com.example.springjpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("person11")
public class PersonController {
    @GetMapping("/")
    public String get(){
        return "ds";
    }

}
