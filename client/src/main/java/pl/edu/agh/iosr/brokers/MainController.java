package pl.edu.agh.iosr.brokers;

import java.io.IOException;

import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.ModelAndView;

import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MainController implements Controller {

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	StockIndexSubscriber publisher = new StockIndexSubscriber("tcp://localhost:61616/");
    	StockIndex index;
    	try {
			publisher.start();
			index = publisher.receive();
		} catch (JMSException e) {
			index = null;
		}
        return new ModelAndView("index", "variable", index);
    }

}
