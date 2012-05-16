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

/** Controller for the page depicting current stock index prices.
 */
public class MainController implements Controller {
	StockIndexDao dao;
	public void setDao(StockIndexDao dao) {
		this.dao = dao;
	}
	
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	Set<StockIndex> index = dao.getAllLatest();
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("currentIndices", index);
        return new ModelAndView("index", "model", model);
    }

}
