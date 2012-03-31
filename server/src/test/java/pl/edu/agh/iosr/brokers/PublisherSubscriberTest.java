package pl.edu.agh.iosr.brokers;

import org.junit.Test;
import static org.junit.Assert.*;

public class PublisherSubscriberTest {
	@Test
	public void testCommunication() throws Exception {
		String url = "vm://localhost";
		StockIndexPublisher publisher = new StockIndexPublisher(url);
		StockIndexSubscriber subscriber = new StockIndexSubscriber(url);
		
		publisher.start();
		subscriber.start();
		
		StockIndex index = new StockIndex("a", null, 123);
		publisher.send(index);
		StockIndex received = subscriber.receive();

		assertEquals("a", received.getName());
		assertEquals(null, received.getValue());
		assertEquals(123, received.getTimestamp()); 
	}
}
