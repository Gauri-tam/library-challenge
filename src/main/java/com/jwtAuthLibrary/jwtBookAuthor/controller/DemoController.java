package com.jwtAuthLibrary.jwtBookAuthor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth/v1/demo-controller")
public class DemoController {

    @GetMapping
    public String getData(){
        return "happy now ! ";
    }
}
