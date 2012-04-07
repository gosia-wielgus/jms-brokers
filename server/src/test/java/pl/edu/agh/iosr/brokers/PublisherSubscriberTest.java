package pl.edu.agh.iosr.brokers;

import java.util.Collections;

import org.junit.Test;
import static org.junit.Assert.*;

public class PublisherSubscriberTest {
	@Test
	public void testCommunication() throws Exception {
		String url = "vm://localhost";
		StockIndexPublisher publisher = new StockIndexPublisher(url);
		StockIndexSubscriber subscriber = new StockIndexSubscriber(url, Collections.singletonList("a"));
		
		publisher.start();
		subscriber.start();
		
		StockIndex index = new StockIndex("a", "A", "125", 123);
		publisher.send(index);
		StockIndex received = subscriber.receive();

		assertEquals("a", received.getKey());
		assertEquals("125", received.getValue().toPlainString());
		assertEquals(123, received.getTimestamp()); 
	}
}
