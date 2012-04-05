package pl.edu.agh.iosr.brokers;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class MainControllerTest {
	@Test
	public void testModel() throws Exception {
		MainController controller = new MainController();
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("index", modelAndView.getViewName());
        assertNotNull(modelAndView.getModel());
	}
}
