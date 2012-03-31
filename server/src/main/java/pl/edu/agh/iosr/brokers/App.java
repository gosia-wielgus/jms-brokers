package pl.edu.agh.iosr.brokers;

import java.util.Properties;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        
        Properties properties = new Properties();
        //properties.load(this.getClass().getResourceAsStream("hello.properties"));
        Context context = new InitialContext(properties);
        
        TopicConnectionFactory connectionFactory = (TopicConnectionFactory) context.lookup("qpidConnectionfactory");
        Connection connection = connectionFactory.createConnection(); 
        connection.start();

        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        Destination destination = (Destination) context.lookup("topicExchange"); 

        MessageProducer messageProducer = session.createProducer(destination);  
        MessageConsumer messageConsumer = session.createConsumer(destination);  

        TextMessage message = session.createTextMessage("Hello world!");
        messageProducer.send(message);

        message = (TextMessage)messageConsumer.receive();    
        System.out.println(message.getText());

        connection.close(); 
        context.close();
    }
}
