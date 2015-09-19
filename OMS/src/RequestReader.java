import java.util.LinkedList;

import org.apache.log4j.Logger;

import Utility.Logging;


public class RequestReader
{
	public static LinkedList<String> requestQueue = null;
	public static RequestReader processor = null;
	public static Logger log = null;
	
	public static RequestReader getRequestProcessor() 
	{
		try
		{
			if (processor != null)
				return processor;
			else
			{
				processor = new RequestReader();
				requestQueue = new LinkedList<String>();
				log = Logging.getLogger();
				new Thread(new RequestProcessor()).start();
				
			}
		}catch(Exception e)
		{
			log.error(e.getMessage());
		}
		return processor;
	}
	
	public synchronized void addQueue(String data)
	{
		requestQueue.add(data);
	}
	
	public synchronized LinkedList<String> cloneQueue ()
	{
		LinkedList<String> copy = (LinkedList<String>) requestQueue.clone();
		requestQueue.clear();
		return copy;
	}
	
	public long getSize ()
	{
		return Long.parseLong(""+ requestQueue.size());
	}
}
