package com.ilinksolutions.uscis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

//	import com.ilinksolutions.uscis.data.DBTest;
import com.ilinksolutions.uscis.file.POCFileRouter;
import com.ilinksolutions.uscis.jms.POCJMSRouter;

@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class POCPilotInvoker
{
    public static void main(String[] args)
    {
        SpringApplication.run(POCPilotInvoker.class, args);
        POCJMSRouter jmsRouter = null;
        POCFileRouter fileRouter = null;
        //DBTest dbTest = null;
        try
        {
        	System.out.println("POCPilotInvoker: STREAMED.");
        	fileRouter = new POCFileRouter();
        	System.out.println("POCPilotInvoker: The File Route returned: " + fileRouter.execute());
        	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        	
        	Thread.sleep(10000);
        	
        	jmsRouter = new POCJMSRouter();
			System.out.println("POCPilotInvoker: The JMS Route returned: " + jmsRouter.execute());
        	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        	
        	/*
        	dbTest = new DBTest();
        	System.out.println("POCPilotInvoker: The DB Test Route returned: " + dbTest.testDBConnection());
        	System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        	*/
		}
        catch (Exception e)
        {
        	System.out.println("POCPilotInvoker: Exception Condition: " + e.getMessage());
        	e.printStackTrace();
		}
    }
}