package pl.edu.agh.iosr.brokers;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

public class JMSStockIndexListener implements MessageListener {
	
	private StockIndexDao dao;
	
	@Override
	public void onMessage(Message message) {
		ObjectMessage objectMessage = (ObjectMessage) message;
		try {
			StockIndex index = (StockIndex) objectMessage.getObject();
			dao.saveStockIndex(index);
		} catch (JMSException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void setDao(StockIndexDao dao) {
		this.dao = dao;
	}
}
