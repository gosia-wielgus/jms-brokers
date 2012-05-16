package pl.edu.agh.iosr.brokers;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/** A publisher which can send stock data to a given ActiveMQ connector URL.
 * 
 * After calling <tt>start</tt> method publisher is ready to accept messages to 
 * send.  
 */
public class StockIndexPublisher {
	private Session session;
	private Connection connection;
	private Map<String, MessageProducer> producers = new HashMap<String, MessageProducer>();
	private String url;
	
	public StockIndexPublisher(String url) {
		this.url = url;
	}
	
	public void start() throws JMSException {

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
	}

	private MessageProducer createTopic(String name) throws JMSException {
		// Create the destination (Topic or Queue)
        Topic destination = session.createTopic(name);
        
        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        producers.put(name, producer);
        return producer;
	}
	
	private MessageProducer getProducer(String topicName) throws JMSException {
		if (producers.containsKey(topicName))
			return producers.get(topicName);
		return createTopic(topicName);
	}
	
	public void close() throws JMSException {
        session.close();
        connection.close();
	}
	
	public void send(StockIndex index) throws JMSException {
		MessageProducer producer = getProducer("brokers." + index.getKey());
		
        ObjectMessage message = (ObjectMessage) session.createObjectMessage(index);

        producer.send(message);
	}
}
