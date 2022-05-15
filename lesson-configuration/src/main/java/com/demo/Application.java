package com.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnableConfigurationProperties
public class Application {

    @Value("${configuration.projectName}")
    private void setProjectName(String projectName) {
        System.out.println("setting project name: " + projectName);
    }

    @Autowired
    private void setEnvironment(Environment env) {
        System.out.println("setting environment: " + env.getProperty("configuration.projectName"));
    }

    @Autowired
    private void setConfigurationProjectProperties(ConfigurationProjectProperties props) {
        System.out.println("ConfigurationProjectProperties.projectName: " + props.getProjectName());
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

}

@Component
@ConfigurationProperties("configuration")
class ConfigurationProjectProperties {

    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
