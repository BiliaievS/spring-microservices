package com.demo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Autowired
    void setEnvironment(Environment env) {
        System.out.println(env.getProperty("configuration.projectName"));
    }
}


@RestController
@RefreshScope
class ProjectNameRestController {

    @Value("${configuration.projectName}")
    private String projectName;

    @RequestMapping("/project-name")
    String projectName() {
        return projectName;
    }
}