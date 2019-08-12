package com.ilinksolutions.uscis.file;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 */
public class POCFileRouter
{
	private int returnValue = 0;
	
    public int execute() throws Exception
    {
    	System.out.println("POCFileRouter: execute: Begin.");
        CamelContext context = new DefaultCamelContext();
        context.addRoutes(new RouteBuilder() 
        {
            @Override
            public void configure()
            {
                from("file:data/inbox?noop=true")
                .to("file:data/outbox");
                returnValue = 1;
            }
        });
        context.start();
        Thread.sleep(10000);
        context.stop();
        System.out.println("POCJMSRouter: execute: End with returnValue: " + returnValue + ".");
        return returnValue;
    }
}