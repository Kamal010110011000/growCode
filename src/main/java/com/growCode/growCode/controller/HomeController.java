package com.growCode.growCode.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/home")
public class HomeController {
    
    @RequestMapping("")
    public String Home(){
        return "Welcome to Grow Code";
    }
}
