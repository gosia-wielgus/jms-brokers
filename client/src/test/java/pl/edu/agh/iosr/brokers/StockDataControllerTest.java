package pl.edu.agh.iosr.brokers;

import java.io.OutputStream;
import java.util.Collections;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.sql.ordering.antlr.CollationSpecification;
import org.hsqldb.Collation;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class StockDataControllerTest {
	StockDataController controller;
	@Test
	public void testHandleRequest() throws Exception {
		StockIndexDao dao = mock(StockIndexDao.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		ServletOutputStream stream = mock(ServletOutputStream.class);
		
		StockIndex stockIndex = new StockIndex("key1", "name1", "1234", "-125", 123456);
		
		when(dao.getAllLatest()).thenReturn(Collections.singleton(stockIndex));
		when(response.getOutputStream()).thenReturn(stream);
		
		controller = new StockDataController();
		controller.setDao(dao);
		
		assertNull(controller.handleRequest(request, response));
		
		verify(response).addHeader("Content-Type", "application/json");
		verify(stream).println("{\"stock_indices\":{\"key1\":{\"timestamp\":123456,\"change\":\"-125\",\"name\":\"name1\",\"value\":\"1234\",\"key\":\"key1\"}}}");


	}
}
