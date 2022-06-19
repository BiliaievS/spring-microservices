package com.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
public class GreetingRestController {

    @GetMapping("/hi")
    Map<String, Object> hi() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", UUID.randomUUID().toString());
        map.put("content", "Hello, World!");
        return map;
    }
}
