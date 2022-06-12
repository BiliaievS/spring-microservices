package com.demo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {


    @RequestMapping("/")
    public String hello() throws InterruptedException {
        Thread.sleep(1500);
        return "Hello World";
    }
}
