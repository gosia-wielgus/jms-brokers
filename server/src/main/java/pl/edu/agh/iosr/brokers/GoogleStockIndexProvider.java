package pl.edu.agh.iosr.brokers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.jsoup.nodes.Document;

public class GoogleStockIndexProvider extends AbstractStockIndexProvider {
	
	private static final String GOOGLE_URL = "http://finance.google.com/finance/info?client=ig&q=";

	static Logger logger = Logger.getLogger(GoogleStockIndexProvider.class);
	
	int interval;
	List<StockIndex> indices;
	Date lastDate = new Date(0);

	private Document doc;

	private SimpleDateFormat longFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat shortFormatter = new SimpleDateFormat("MM-dd\u00a0HH:mm");
	
	
	public GoogleStockIndexProvider(int pollingInterval, List<StockIndex> indices) {
		this.interval = pollingInterval;
		this.indices = indices;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				dispatchAll();
				Thread.sleep(interval);
			}
		} catch (InterruptedException e) {
			// OK
		}
	}
	
	private void dispatchAll() {		
		for (int i=0; i<indices.size(); i++) {
			StockIndex index = indices.get(i);
			try {
				index = updateStockIndex(index);
			} catch (Exception e) {
				logger.error("Error while retrieving", e);
			}
			logger.info("Dispatched "+index);
			dispatch(index);
			indices.set(i, index);
		}
	}

	private StockIndex updateStockIndex(StockIndex index) throws Exception {

	    URL url = new URL(GOOGLE_URL+index.getName());
	    URLConnection conn = url.openConnection();
	    BufferedReader isr = new BufferedReader(new InputStreamReader(conn.getInputStream()));

	    String result = "";
	    while (true) {
	    	String line = isr.readLine();
	    	if (line == null) break;
	    	result += line;
	    }
	    result = result.replace("//", "");
	    JSONTokener tok = new JSONTokener(result);
	    JSONArray arr = new JSONArray(tok);
	    JSONObject ob = (JSONObject)arr.get(0);
	    String value = ob.getString("l");
	    String change = ob.getString("c");
	    String title = ob.getString("t");
	    return new StockIndex(index.getKey(), title, value, change, new Date().getTime());
	}
}
