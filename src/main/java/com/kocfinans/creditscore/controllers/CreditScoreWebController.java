package com.kocfinans.creditscore.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CreditScoreWebController {

    @GetMapping("/")
    public String serveWebPage() {
        return "index";
    }
}
