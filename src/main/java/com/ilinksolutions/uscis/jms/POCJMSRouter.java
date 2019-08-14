package com.ilinksolutions.uscis.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 */
public class POCJMSRouter
{
	private int returnValue = 0;
	
    public int execute() throws Exception
    {
    	System.out.println("POCJMSRouter: execute: Begin.");
        CamelContext context = new DefaultCamelContext();
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://ilink-amq-amq-1-4x5xk:61616");
        connectionFactory.setUserName("ilink");
        connectionFactory.setPassword("ilink01");
        context.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));
        context.addRoutes(new RouteBuilder() 
        {
            @Override
            public void configure()
            {
            	System.out.println("POCJMSRouter: execute(): configure(): ");
                from("file:data/outbox?fileName=person.xml&noop=true")
                .process(new Processor()
                {                    
                    public void process(Exchange exchange) throws Exception
                    {
                        System.out.println("We just downloaded: " + exchange.getIn().getMessageId());
                    }
                })
                .to("jms:ilinkq1");
                returnValue = 1;
            }
        });
        context.start();
        Thread.sleep(30000);
        context.stop();
        System.out.println("POCJMSRouter: execute: End with returnValue: " + returnValue + ".");
        return returnValue;
    }
}