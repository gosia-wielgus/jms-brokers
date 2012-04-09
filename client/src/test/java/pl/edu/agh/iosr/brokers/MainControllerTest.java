package pl.edu.agh.iosr.brokers;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;

public class MainControllerTest {
	MainController controller;
	StockIndexDao dao;
	
	@Before
	public void setUp() {
		controller = new MainController();
		dao = mock(StockIndexDao.class);
		controller.setDao(dao);
	}
	
	@Test
	public void testModelContainsStockIndex() throws Exception {
		StockIndex index = new StockIndex("abc", "A B C", "123", "0", 1234);
		Set<StockIndex> indices = Collections.singleton(index);
		when(dao.getAllLatest()).thenReturn(indices);
		
        ModelAndView modelAndView = controller.handleRequest(null, null);
        assertEquals("index", modelAndView.getViewName());
        assertSame(indices, ((Map)modelAndView.getModel().get("model")).get("currentIndices"));
	}
}
