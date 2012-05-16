package pl.edu.agh.iosr.brokers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.jms.JMSException;
import javax.management.RuntimeErrorException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Cotroller for sending JSON-encoded current stock index data */
public class StockDataController implements Controller {
	StockIndexDao dao;
	public void setDao(StockIndexDao dao) {
		this.dao = dao;
	}
	
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	try {
	    	Set<StockIndex> indices = dao.getAllLatest();
	    	response.addHeader("Content-Type", "application/json");
	    	JSONObject jsonIndices = new JSONObject();
			for (StockIndex index : indices) {
				jsonIndices.put(index.getKey(), serialize(index));
			}

	    	JSONObject root = new JSONObject();
	    	root.put("stock_indices", jsonIndices);
	    	
	    	response.getOutputStream().println(root.toString());
	    	return null;
    	} catch (JSONException e) {
    		throw new RuntimeException(e);
		}
    }
    
    public JSONObject serialize(StockIndex index) {
    	try {
        	JSONObject object = new JSONObject();
	    	object.put("key", index.getKey());
	    	object.put("name", index.getName());
	    	object.put("value", ""+index.getValue());
	    	object.put("change", ""+index.getChange());
	    	object.put("timestamp", index.getTimestamp());
	    	return object;
    	} catch (JSONException e) {
    		throw new RuntimeException(e);
    	}
    }
}
