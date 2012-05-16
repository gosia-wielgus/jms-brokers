package pl.edu.agh.iosr.brokers;

import java.util.List;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;

/** A simple threaded stock index data receiver listening on ActiveMQ URL. 
 */
public class StockIndexSubscriber {
	private MessageConsumer consumer;
	private Session session;
	private Connection connection;
	private String url;
	private List<String> stockNames;
	public StockIndexSubscriber(String url, List<String> stockNames) {
		this.url = url;
		this.stockNames = stockNames;
	}
	
	public void start() throws JMSException {
        // Create a ConnectionFactory
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        // Create a Connection
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        String topics = "";
        for (String name : stockNames) {
        	topics += ",brokers."+name;
        }
        topics = topics.substring(1);
        // Create the destination (Topic or Queue)
        Topic destination = session.createTopic(topics);

        // Create a MessageConsumer from the Session to the Topic or Queue
        consumer = session.createConsumer(destination);
	}
	
	public void clean() throws JMSException {
		consumer.close();
        session.close();
        connection.close();
	}
	
	public StockIndex receive() throws JMSException {
		ObjectMessage message = (ObjectMessage)consumer.receive(1000);
		StockIndex index = (StockIndex)message.getObject();
		return index;
	}
}
