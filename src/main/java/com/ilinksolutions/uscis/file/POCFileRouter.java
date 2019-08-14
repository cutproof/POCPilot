package com.ilinksolutions.uscis.file;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 */
public class POCFileRouter
{
	BufferedWriter writer = null;
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
                from("file:data\\inbox?noop=true")
                .process(new Processor()
                {                    
                    public void process(Exchange exchange) throws Exception
                    {
                    	try
                    	{
                    		System.out.println("POCFileRouter: process: WRITING FILE: STARTED.");
                    	    writer = new BufferedWriter(new FileWriter("data\\outbox\\person.xml", false));
                    	    writer.append("<?xml version=\"1.0\"?>" +
                    	    		"<person>" +
                    	    			"<id>0001</id>" +
                    	    			"<FirstName>Tanmay</FirstName>" +
                    	    			"<LastName>Patil</LastName>" +
                    	    			"<ContactNo>1234567890</ContactNo>" +
                    	    			"<Email>tanmaypatil@xyz.com</Email>" +
                    	    			"<image></image>" +
                    	    		"</person>");
                    	    writer.flush();
                    	}
                    	catch(IOException ioe)
                    	{
                    		System.out.println("POCFileRouter: process: IO Exception: " + ioe.getMessage());
                    	}
                    	finally
                    	{
                    		writer.close();
                    		System.out.println("POCFileRouter: process: WRITING FILE: ENDED.");
                    	}
                    }
                })
                .to("file:data\\outbox");
                returnValue = 1;
            }
        });
        context.start();
        Thread.sleep(5000);
        context.stop();
        System.out.println("POCFileRouter: execute: End with returnValue: " + returnValue + ".");
        return returnValue;
    }
}