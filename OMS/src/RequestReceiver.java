import org.apache.log4j.Logger;

import Utility.Logging;


public class RequestReceiver {
	
	Logger log = Logging.getLogger();
	public void requestReceiver (String data)
	{
		try
		{
			log.info("REQUEST RECIEVED FROM UPSTREAM :: " + data);
			RequestReader.getRequestProcessor().addQueue(data);
			log.info("Send To Request Queue ....");
			
		}catch(Exception e)
		{
			log.error(e.getMessage());
		}
	}

}
