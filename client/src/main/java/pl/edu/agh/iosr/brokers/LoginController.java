package pl.edu.agh.iosr.brokers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class LoginController {
    @RequestMapping("/login")
    public ModelAndView showLoginForm() {
    	return new ModelAndView("login");
    }
    
	public void setDao(StockIndexDao dao){}
}
