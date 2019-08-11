package com.ilinksolutions.uscis;
 
import org.springframework.stereotype.Component;
 
@Component(value = "pocpilot")
public class POCPilot 
{
	POCRoutes routes = null;
	String returnValue = "failure";
	
    public String transform()
    {
    	routes = new POCRoutes();
    	try
    	{
    		routes.execute();
    		returnValue = "success";
    	}
    	catch(Exception e)
    	{
    		System.out.println("POCPilot: transform: Exception: " + e.getMessage());
    	}
    	/*
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < 3; i++)
        {
            int number = (int) (Math.round(Math.random() * 1000) % 10);
            char letter = (char) ('0' + number);
            buffer.append(letter);
        }
        return "Harjeet Parmar: Current Buffer is: " + buffer.toString();
        */
    	return returnValue;
    }
}