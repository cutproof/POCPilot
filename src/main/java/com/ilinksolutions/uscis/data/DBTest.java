package com.ilinksolutions.uscis.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBTest
{
	private int returnValue;
	public int testDBConnection()
	{
		try
		{
			String databaseURL = "jdbc:postgresql://";
			databaseURL += System.getenv("ILINKSERVICE_SERVICE_HOST");
			databaseURL += ":" + System.getenv("ILINKSERVICE_SERVICE_PORT");
			databaseURL += "/" + System.getenv("POSTGRESQL_DATABASE");
			String username = System.getenv("POSTGRESQL_USER");
			String password = System.getenv("POSTGRESQL_PASSWORD");
			Connection connection = DriverManager.getConnection(databaseURL, username, password);

			if (connection != null)
			{
				String SQL = "select * from public.person";
				Statement stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(SQL);
				while (rs.next())
				{
					//rs.getString("first"), rs.getString("second"), rs.getString("noun"));
				}
				rs.close();
				connection.close();
			}
		}
		catch (Exception e)
		{
			System.out.println("DBTest: testDBConnection: " + e.getMessage());
			e.printStackTrace();
		}
		return returnValue;
	}
}