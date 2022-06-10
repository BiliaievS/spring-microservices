package com.demo;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class LoggerExample {

    private static final org.apache.commons.logging.Log commonsLoggingLog = org.apache.commons.logging.LogFactory.getLog(LoggerExample.class);
    private static final org.slf4j.Logger sl4jLog = org.slf4j.LoggerFactory.getLogger(LoggerExample.class);
    private static final org.jboss.logging.Logger jbossLog = org.jboss.logging.Logger.getLogger(LoggerExample.class);
    private static final java.util.logging.Logger javaUtilLog = java.util.logging.Logger.getLogger(LoggerExample.class.getName());
    private static final org.apache.log4j.Logger log4jLogger = org.apache.log4j.Logger.getLogger(LoggerExample.class);
    private static final org.apache.juli.logging.Log tomcatJuliLog = org.apache.juli.logging.LogFactory.getLog(LoggerExample.class);

    @PostConstruct
    public void logSomething() {
        commonsLoggingLog.info("Via commonsLogging");
        sl4jLog.info("Via sl4jLog");
        jbossLog.info("Via jbossLog");
        javaUtilLog.info("Via javaUtilLog");
        log4jLogger.info("Via log4jLogger");
        tomcatJuliLog.info("Via JULI");

        commonsLoggingLog.error("Something bad ", new IOException("Commons down"));
    }
}
