import java.sql.PreparedStatement;
import java.util.LinkedList;

import org.apache.log4j.Logger;

import db.Database;
import Utility.Logging;


public class RequestProcessor implements Runnable {

	static Logger log = Logging.getLogger();
	RequestReader reader = RequestReader.getRequestProcessor();
	@Override
	public void run() {
		try
		{
			log.info("Request Processor Thread Started ...");
			while (true)
			{
				LinkedList<String> requestList = new LinkedList<String>();
				if (reader.getSize() > 0)
				{
					requestList = reader.cloneQueue();
					int count = RequestProcessor.bulkInsert(requestList);
					log.info("DB Insert Successfull :: " + count);
				}else
				{
					log.info("Request PRocessor Thread Sleep for 20,000ms ...");
					Thread.sleep(20000);
				}
			}
				
		}catch(Exception e)
		{
			log.error(e);
		}
	}
	
	public static int bulkInsert (LinkedList<String> list)
	{
		String insertQuery 		= "insert into item_table (sno,ITEM_ID,MERCHANT_ID,marketplace_id,priority,data_type,payload,request_status,estimate_time)  values (sno_item_seq.nextval,?,?,?,?,?,?,?, (select sysdate+((select time_in_seconds from sla_table where priority=?)/24/60/60) from dual))";
		PreparedStatement ps 	= null;
		int counter = 0;
		try
		{
			ps = Database.getDatabase().connect.prepareStatement(insertQuery);
			Database.getDatabase().connect.setAutoCommit(false);
			
			while (list.size() > 0)
			{
				String data = list.removeFirst();
				String[] splitData = data.split(",");
				ps.setString(1, splitData[0]);
				ps.setLong(2, Integer.parseInt(splitData[1]));
				ps.setLong(3, Integer.parseInt(splitData[2]));
				ps.setString(4, splitData[3]);
				ps.setString(5, splitData[4]);
				ps.setString(6, splitData[5]);
				ps.setInt(7, 2);
				ps.setString(8, splitData[3]);
				
				ps.addBatch();
				counter++;
			}
			
			ps.executeBatch();
			Database.getDatabase().connect.commit();
			log.info("Counter :: " + counter);
			
		}catch(Exception e)
		{
			log.error(e);
		}
		return counter;
	}
}
