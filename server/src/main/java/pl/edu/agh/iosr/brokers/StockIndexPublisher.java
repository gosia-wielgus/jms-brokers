package pl.edu.agh.iosr.brokers;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

public class StockIndexPublisher {
	private Session session;
	private MessageProducer producer;
	private Connection connection;
	String url;
	
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

        // Create the destination (Topic or Queue)
        Topic destination = session.createTopic("TEST.FOO");

        // Create a MessageProducer from the Session to the Topic or Queue
        producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

	}
	
	public void close() throws JMSException {
        session.close();
        connection.close();
	}
	
	public void send(StockIndex index) throws JMSException {
        ObjectMessage message = (ObjectMessage) session.createObjectMessage(index);

        System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
        producer.send(message);
	}
}
