package com.ilinksolutions.uscis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

import com.ilinksolutions.uscis.jms.POCJMSRouter;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class POCPilotInvoker
{
    public static void main(String[] args)
    {
        SpringApplication.run(POCPilotInvoker.class, args);
        POCJMSRouter jmsRouter = new POCJMSRouter();
        try
        {
			System.out.println("The JMS Route returned: " + jmsRouter.execute());
		}
        catch (Exception e)
        {
        	System.out.println("The JMS Route returned: " + e.getMessage());
        	e.printStackTrace();
		}
    }
}