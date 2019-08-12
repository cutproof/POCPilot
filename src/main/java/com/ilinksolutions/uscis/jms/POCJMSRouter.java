package com.ilinksolutions.uscis.jms;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * A route that polls an FTP server for new orders, downloads them, converts the order 
 * file into a JMS Message and then sends it to the JMS incomingOrders queue hosted 
 * on an embedded ActiveMQ broker instance.
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
            	System.out.println("");
                from("file:data/outbox?noop=true")
                .process(new Processor()
                {                    
                    public void process(Exchange exchange) throws Exception
                    {
                        System.out.println("We just downloaded: " + exchange.getIn().getHeader("CamelFileName"));
                    }
                })
                .to("jms:ilinkq1");
                returnValue = 1;
            }
        });
        context.start();
        Thread.sleep(10000);
        context.stop();
        System.out.println("POCJMSRouter: execute: End.");
        return returnValue;
    }
}