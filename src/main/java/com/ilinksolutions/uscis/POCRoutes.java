package com.ilinksolutions.uscis;

import javax.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.JmsTransactionManager;

@Slf4j
@Component
@Configuration
public class POCRoutes
{
	String returnValue = "Failure!";
	public String execute() throws Exception
	{
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder()
		{
			public void configure()
			{
				from("file:data/inbox?fileName=person.xml&noop=true").
				to("jms:ilinkq1").log(LoggingLevel.DEBUG, log, "*********** New message received ***********").
					process(exchange ->
	        		{
	        			System.out.println("Exchange: Converting Message: *********** Exchange: Started ***********");
	                    String convertedMessage = (String)exchange.getIn().getBody();
	                    System.out.println("The Converted Message is: " + convertedMessage);
	                    exchange.getOut().setBody(convertedMessage);
	                    System.out.println("Exchange: Converting Message: *********** Exchange: Ended ***********");
	                    returnValue = "POCRoutes: Success!";
	                }
				);
				
                //from("file:data/inbox?noop=true").to("file:data/outbox");
			/*	from("file:src/data?noop=true").to("{{input.queue}}");

		        // content-based router
		        from("jms:incomingOrders")
		        .choice()
		            .when(header("CamelFileName").endsWith(".xml"))
		                .to("jms:topic:xmlOrders")  
		            .when(header("CamelFileName").endsWith(".csv"))
		                .to("jms:topic:csvOrders");

		        from("jms:topic:xmlOrders").to("jms:accounting");  
		        from("jms:topic:xmlOrders").to("jms:production");  
		        
		        // test that our route is working
		        from("jms:accounting").process(new Processor() {
		            public void process(Exchange exchange) throws Exception {
		                System.out.println("Accounting received order: "
		                        + exchange.getIn().getHeader("CamelFileName"));  
		            }
		        });
		        from("jms:production").process(new Processor() {
		            public void process(Exchange exchange) throws Exception {
		                System.out.println("Production received order: "
		                        + exchange.getIn().getHeader("CamelFileName"));  
		            }
		        });
		        */
			}
		});
        context.start();
        Thread.sleep(60000);
        context.stop();
        return returnValue;
    }

    @Bean
    public JmsTransactionManager jmsTransactionManager(final ConnectionFactory connectionFactory)
    {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(connectionFactory);
        return jmsTransactionManager;
    }

    @Bean
    public JmsComponent jmsComponent(final ConnectionFactory connectionFactory, final JmsTransactionManager jmsTransactionManager)
    {
        JmsComponent jmsComponent = JmsComponent.jmsComponentTransacted(connectionFactory, jmsTransactionManager);
        return jmsComponent;
    }
}
