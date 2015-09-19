package Utility;

	import java.io.IOException;
	import org.apache.log4j.Logger;
	import org.apache.log4j.ConsoleAppender;
	import org.apache.log4j.Level;
	import org.apache.log4j.PatternLayout;
	import org.apache.log4j.RollingFileAppender;
	 
	public class Logging {
	 
	private static Logger log = null;  
	 
	public static Logger getLogger()
	{
		if (log == null)
		{
	log = Logger.getLogger("OMC");  
	//This is the root logger provided by log4j
	Logger rootLogger = Logger.getRootLogger();
	rootLogger.setLevel(Level.ERROR);
	 
	//Define log pattern layout
	PatternLayout layout = new PatternLayout("%d{ISO8601} [%t] %-5p %c %x - %m%n");
	 
	//Add console appender to root logger
	rootLogger.addAppender(new ConsoleAppender(layout));
	try
	{
	//Define file appender with layout and output log file name
	RollingFileAppender fileAppender = new RollingFileAppender(layout, "D:\\KashiF\\PERSONAL\\Amazing-Hack\\log\\demoApplication.log");
	 
	//Add the appender to root logger
	rootLogger.addAppender(fileAppender);
	}
	catch (IOException e)
	{
	System.out.println("Failed to add appender !!");
	}
	//Let verify the log messages
	log.info("Welcome to Amazing-Hackathon ...");
	}
	return log;
}
}
