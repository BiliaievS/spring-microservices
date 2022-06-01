package com.demo;

import org.springframework.stereotype.Service;

@Service
public class EchoService {

    public String echo(String input) {
        return "Echo: " + input;
    }
}
