package com.example.SpringBootProject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class TestController {
    @GetMapping("/index")
    public String indexPage(){
        return "index";
    }
}
