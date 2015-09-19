package db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.log4j.Logger;

import Utility.Logging;

public class Database {
	
	public static Connection connect 		= null;
	public static Database db = null;
	Logger log = Logging.getLogger();
	
	public Database()
	{
		try{  
			Class.forName("oracle.jdbc.driver.OracleDriver");  
			  
			connect = DriverManager.getConnection(  
			"jdbc:oracle:thin:@192.168.7.153:1521:amazon","hackathon","hackathon");
			
			log.info("CONNECTED TO DB SUCCESSFULLY ....");
			}catch(Exception e)
		{
				log.error("DB ERROR ::: " + e);
		} 
	}
	
	public static Database getDatabase()
	{
		if (connect == null)
		{
			db = new Database();
			return db;
		}else{
			return db;
		}
	}
}
