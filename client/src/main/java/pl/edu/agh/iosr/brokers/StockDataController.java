package pl.edu.agh.iosr.brokers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StockDataController implements Controller {
	StockIndexDao dao;
	public void setDao(StockIndexDao dao) {
		this.dao = dao;
	}
	
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	Set<StockIndex> index = dao.getAllLatest();
    	response.addHeader("Content-Type", "text/plain");
    	//response.getOutputStream();
    	return null;
    }

}
