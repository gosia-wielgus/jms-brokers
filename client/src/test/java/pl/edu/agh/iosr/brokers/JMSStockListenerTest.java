package pl.edu.agh.iosr.brokers;

import javax.jms.ObjectMessage;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class JMSStockListenerTest {

	JMSStockIndexListener listener;
	
	@Test
	public void testOnMessage() throws Exception {
		StockIndexDao dao = mock(StockIndexDao.class);
		ObjectMessage message = mock(ObjectMessage.class);
		StockIndex index = mock(StockIndex.class);
		listener = new JMSStockIndexListener();
		listener.setDao(dao);
		
		when(message.getObject()).thenReturn(index);
		
		listener.onMessage(message);
		
		verify(dao).saveStockIndex(index);
	}
}
