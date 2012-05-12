package pl.edu.agh.iosr.brokers;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;


public class App 
{
    public static void main( String[] args ) throws Exception {
        BrokerService broker = new BrokerService();

        String url = "ssl://localhost:61616";
        
        List<StockIndex> indices = new ArrayList<StockIndex>();
        indices.add(new StockIndex("pl.wig", "WIG", "10000", "0", 0));
        indices.add(new StockIndex("pl.wig20", "WIG20", "1000", "0", 0));
	     // configure the broker
	    broker.addConnector(url);

	    broker.start();
	    
	    final StockIndexPublisher publisher = new StockIndexPublisher(url);
	    publisher.start();
	    
	    BankierStockIndexProvider provider = new BankierStockIndexProvider(30000, indices);
	    provider.setOnStockIndex(new StockIndexListener() {
			
			@Override
			public void onStockIndex(StockIndex index) {
				try {
					publisher.send(index);
				} catch (JMSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	    provider.start();
    }
/*
    public static void doRabbit() throws Exception {
        
        Properties properties = new Properties();
        //properties.load(this.getClass().getResourceAsStream("hello.properties"));
        properties.setProperty("java.naming.factory.initial", 
        		"org.apache.qpid.jndi.PropertiesFileInitialContextFactory");
        properties.setProperty("connectionfactory.qpidConnectionfactory",
        		"amqp://guest:guest@clientid/test?brokerlist='tcp://localhost:5672'");
        properties.setProperty("destination.topicExchange", "amq.topic");
        if (true) {            Context context = new InitialContext(properties);

        ConnectionFactory connectionFactory = (ConnectionFactory) context.lookup("qpidConnectionfactory");
        Connection connection = connectionFactory.createConnection();
        connection.start();

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = (Destination) context.lookup("topicExchange");

        MessageProducer messageProducer = session.createProducer(destination);
        MessageConsumer messageConsumer = session.createConsumer(destination);

        TextMessage message = session.createTextMessage("Hello world!");
        messageProducer.send(message);

        message = (TextMessage)messageConsumer.receive();
        System.out.println(message.getText());

        connection.close();
        context.close();
        } else {
        Context context = new InitialContext(properties);
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context.lookup("qpidConnectionfactory");
        
        
        TopicConnection connection = connectionFactory.createTopicConnection(); 
        connection.start();

        TopicSession session=connection.createTopicSession(false,Session.AUTO_ACKNOWLEDGE);
        //Destination destination = (Destination) context.lookup("topicExchange"); 
        Topic topic = (Topic) context.lookup("topicExchange");

        TopicPublisher messageProducer = session.createPublisher(topic);  
        //MessageConsumer messageConsumer = session.createConsumer(destination);  

        TextMessage message = session.createTextMessage("Hello world!");
        messageProducer.publish(topic, message);

        //message = (TextMessage)messageConsumer.receive();    
        //System.out.println(message.getText());

        connection.close(); 
        context.close();
        }
    }*/
}
