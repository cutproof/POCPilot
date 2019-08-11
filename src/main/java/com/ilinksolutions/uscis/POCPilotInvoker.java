package com.ilinksolutions.uscis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class POCPilotInvoker
{
    public static void main(String[] args)
    {
        SpringApplication.run(POCPilotInvoker.class, args);
    }
}