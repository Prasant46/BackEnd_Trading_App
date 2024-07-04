package com.app.trading.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String home(){

        return "WELCOME PRASANT";
    }


    @GetMapping("/api")
    public String secure(){

        return "WELCOME PRASANT.. This is Secure";
    }
}
