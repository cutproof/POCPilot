package com.ilinksolutions.uscis;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

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
				//	from("file:c://data//inbox?fileName=person.xml&noop=true").to("file:c://data/outbox//");
				from("file:c://data//inbox?fileName=person.xml&noop=true").to("{{input.queue}}");
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

        // start the route and let it do its work
        context.start();
        Thread.sleep(10000);

        // stop the CamelContext
        context.stop();
        returnValue = "Success!";
        return returnValue;
    }

}
