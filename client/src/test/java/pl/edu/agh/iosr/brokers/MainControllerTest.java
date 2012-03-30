package pl.edu.agh.iosr.brokers;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import static org.junit.Assert.*;

public class MainControllerTest {
	@Test
	public void testModel() throws Exception {
		MainController controller = new MainController();
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("index", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
	}
}
