package pl.edu.agh.iosr.brokers;

import org.springframework.jms.listener.DefaultMessageListenerContainer;

public class ContainerConfig {
	public void setDefaultMessageListenerContainer(DefaultMessageListenerContainer c) {
		System.out.println("GOT IT!!!!!!");
		System.out.println(""+c.getMessageListener()+";;"+c.getDestination());
	}
}
